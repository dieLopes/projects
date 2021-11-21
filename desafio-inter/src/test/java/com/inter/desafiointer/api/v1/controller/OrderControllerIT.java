package com.inter.desafiointer.api.v1.controller;

import com.inter.desafiointer.api.v1.BaseIT;
import com.inter.desafiointer.api.v1.dto.order.OrderCreateDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseListDTO;
import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Objects;

import static com.inter.desafiointer.domain.OrderStatus.CANCELLED;
import static com.inter.desafiointer.domain.OrderStatus.OK;
import static com.inter.desafiointer.domain.OrderStatus.PENDING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderControllerIT extends BaseIT {

    private final String ORDER_PATH = "/broker/api/v1/orders/";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenFindAllOrdersThenReturnOrderList() {
        String walletId = getWalletId();
        createOrder("BUY", "BIDI11", 10, walletId);
        createOrder("SELL", "BIDI11", 5, walletId);
        createOrder("BUY", "LREN3", 5, walletId);

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
    public void whenFindOrderByIdThenReturnOrder() {
        String walletId = getWalletId();
        OrderResponseDTO orderCreated = createOrder("BUY", "EGIE3", 10, walletId);

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
    public void whenFindOrdersFilteringByCodeThenReturnOrderList() {
        String walletId = getWalletId();
        createOrder("BUY", "BIDI11", 10, walletId);
        createOrder("SELL", "BIDI11", 5, walletId);
        createOrder("BUY", "LREN3", 5, walletId);
        OrderResponseListDTO orderResponseListDTO = restTemplate
                .getForObject("http://localhost:" + port + ORDER_PATH + "?code=BIDI11", OrderResponseListDTO.class);
        assertEquals(2, orderResponseListDTO.getOrders().size());
    }

    @Test
    public void whenCreateOrderThenReturnOrder() {
        String walletId = getWalletId();
        OrderResponseDTO orderResponseDTO = createOrder("BUY", "BIDI11", 10, walletId);
        assertNotNull(orderResponseDTO.getId());
        assertNotNull(orderResponseDTO.getDate());
        assertEquals("BUY", orderResponseDTO.getType());
        assertEquals(10, orderResponseDTO.getAmount());
        assertEquals(new BigDecimal("66.51"), orderResponseDTO.getUnitPrice());
        assertEquals(new BigDecimal("665.10"), orderResponseDTO.getTotalPrice());
        assertEquals("BIDI11", orderResponseDTO.getCompanyCode());
        assertEquals(walletId, orderResponseDTO.getWalletId());
        assertEquals(PENDING.toString(), orderResponseDTO.getStatus());
    }

    @Test
    public void whenCreateWrongOrderAndGetThenReturnOrderWithCancelledStatus() throws InterruptedException {
        String walletId = getWalletId();
        OrderResponseDTO orderResponseDTO = createOrder("SELL", "MGLU3", 10, walletId);
        assertEquals("PENDING", orderResponseDTO.getStatus());
        Thread.sleep(200);
        OrderResponseDTO updatedOrder = restTemplate.getForObject(
                "http://localhost:" + port + ORDER_PATH + orderResponseDTO.getId(), OrderResponseDTO.class);
        assertEquals(CANCELLED.toString(), updatedOrder.getStatus());
    }

    @Test
    public void whenCreateRightOrderAndGetThenReturnOrderWithOKStatus() throws InterruptedException {
        String walletId = getWalletId();
        OrderResponseDTO orderResponseDTO = createOrder("BUY", "MGLU3", 10, walletId);
        assertEquals("PENDING", orderResponseDTO.getStatus());
        Thread.sleep(200);
        OrderResponseDTO updatedOrder = restTemplate.getForObject(
                "http://localhost:" + port + ORDER_PATH + orderResponseDTO.getId(), OrderResponseDTO.class);
        assertEquals(OK.toString(), updatedOrder.getStatus());
    }

    private OrderResponseDTO createOrder (String type, String code, int amount, String walletId) {
        OrderCreateDTO orderCreateDTO = OrderCreateDTO.Builder.of()
                .walletId(walletId)
                .code(code)
                .type(type)
                .amount(amount)
                .build();
        return restTemplate.postForEntity("http://localhost:" + port + ORDER_PATH, orderCreateDTO,
                OrderResponseDTO.class).getBody();
    }

    public String getWalletId () {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("New User")
                .build();
        ResponseEntity<UserResponseDTO> user = restTemplate.postForEntity(
                "http://localhost:" + port + "/broker/api/v1/users", userCreateDTO, UserResponseDTO.class);
        return Objects.requireNonNull(user.getBody()).getWallet().getId();
    }
}
