package com.inter.desafiointer.api.v1.controller;

import com.inter.desafiointer.api.v1.BaseIT;
import com.inter.desafiointer.api.v1.dto.order.OrderCreateDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseListDTO;
import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseDTO;
import com.inter.desafiointer.api.v1.dto.wallet.WalletResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;

import static com.inter.desafiointer.domain.OrderStatus.CANCELLED;
import static com.inter.desafiointer.domain.OrderStatus.OK;
import static com.inter.desafiointer.domain.OrderStatus.PENDING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIT extends BaseIT {

    private final String ORDER_PATH = "/broker/api/v1/orders/";
    @Autowired
    private TestRestTemplate restTemplate;
    private WalletResponseDTO wallet;

    @BeforeAll
    public void init () {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("New User")
                .build();
        ResponseEntity<UserResponseDTO> user = restTemplate.postForEntity(
                "http://localhost:" + port + "/broker/api/v1/users", userCreateDTO, UserResponseDTO.class);
        wallet = Objects.requireNonNull(user.getBody()).getWallet();
    }

    @Test
    @Order(1)
    public void whenFindAllOrdersThenReturnOrderList() {
        createOrder("BUY", "BIDI11", 10);
        createOrder("SELL", "BIDI11", 5);
        createOrder("BUY", "LREN3", 5);

        OrderResponseListDTO orderResponseListDTO = restTemplate
                .getForObject("http://localhost:" + port + ORDER_PATH, OrderResponseListDTO.class);
        assertEquals(3, orderResponseListDTO.getOrders().size());
        assertEquals(2, orderResponseListDTO.getOrders()
                .stream()
                .filter(order -> order.getCompanyCode().equals("BIDI11"))
                .count());
        assertEquals(1, orderResponseListDTO.getOrders()
                .stream()
                .filter(order -> order.getCompanyCode().equals("LREN3"))
                .count());
    }

    @Test
    @Order(2)
    public void whenFindOrderByIdThenReturnOrder() {
        OrderResponseDTO orderCreated = createOrder("BUY", "EGIE3", 10);

        OrderResponseDTO orderResponseDTO = restTemplate.getForObject(
                "http://localhost:" + port + ORDER_PATH + orderCreated.getId(), OrderResponseDTO.class);

        assertNotNull(orderResponseDTO.getDate());
        assertEquals(orderCreated.getId(), orderResponseDTO.getId());
        assertEquals(orderCreated.getType(), orderResponseDTO.getType());
        assertEquals(orderCreated.getAmount(), orderResponseDTO.getAmount());
        assertEquals(orderCreated.getUnitPrice(), orderResponseDTO.getUnitPrice());
        assertEquals(orderCreated.getTotalPrice(), orderResponseDTO.getTotalPrice());
        assertEquals(orderCreated.getCompanyCode(), orderResponseDTO.getCompanyCode());
        assertEquals(orderCreated.getWalletId(), orderResponseDTO.getWalletId());
    }

    @Test
    @Order(3)
    public void whenFindOrdersFilteringByCodeThenReturnOrderList() {
        OrderResponseListDTO orderResponseListDTO = restTemplate
                .getForObject("http://localhost:" + port + ORDER_PATH + "?code=BIDI11", OrderResponseListDTO.class);
        assertEquals(2, orderResponseListDTO.getOrders().size());
        assertEquals(2, orderResponseListDTO.getOrders()
                .stream()
                .filter(order -> order.getCompanyCode().equals("BIDI11"))
                .count());
    }

    @Test
    @Order(4)
    public void whenCreateOrderThenReturnOrder() {
        OrderResponseDTO orderResponseDTO = createOrder("BUY", "BIDI11", 10);
        assertNotNull(orderResponseDTO.getId());
        assertNotNull(orderResponseDTO.getDate());
        assertEquals("BUY", orderResponseDTO.getType());
        assertEquals(10, orderResponseDTO.getAmount());
        assertEquals(new BigDecimal("66.51"), orderResponseDTO.getUnitPrice());
        assertEquals(new BigDecimal("665.10"), orderResponseDTO.getTotalPrice());
        assertEquals("BIDI11", orderResponseDTO.getCompanyCode());
        assertEquals(wallet.getId(), orderResponseDTO.getWalletId());
        assertEquals(PENDING.toString(), orderResponseDTO.getStatus());
    }

    @Test
    @Order(5)
    public void whenCreateWrongOrderAndGetThenReturnOrderWithCancelledStatus() throws InterruptedException {
        OrderResponseDTO orderResponseDTO = createOrder("SELL", "MGLU3", 10);
        assertEquals("PENDING", orderResponseDTO.getStatus());
        Thread.sleep(200);
        OrderResponseDTO updatedOrder = restTemplate.getForObject(
                "http://localhost:" + port + ORDER_PATH + orderResponseDTO.getId(), OrderResponseDTO.class);
        assertEquals(CANCELLED.toString(), updatedOrder.getStatus());
    }

    @Test
    @Order(6)
    public void whenCreateRightOrderAndGetThenReturnOrderWithOKStatus() throws InterruptedException {
        OrderResponseDTO orderResponseDTO = createOrder("BUY", "MGLU3", 10);
        assertEquals("PENDING", orderResponseDTO.getStatus());
        Thread.sleep(200);
        OrderResponseDTO updatedOrder = restTemplate.getForObject(
                "http://localhost:" + port + ORDER_PATH + orderResponseDTO.getId(), OrderResponseDTO.class);
        assertEquals(OK.toString(), updatedOrder.getStatus());
    }

    private OrderResponseDTO createOrder (String type, String code, int amount) {
        OrderCreateDTO orderCreateDTO = OrderCreateDTO.Builder.of()
                .walletId(wallet.getId())
                .code(code)
                .type(type)
                .amount(amount)
                .build();
        return restTemplate.postForEntity("http://localhost:" + port + ORDER_PATH, orderCreateDTO,
                OrderResponseDTO.class).getBody();
    }
}
