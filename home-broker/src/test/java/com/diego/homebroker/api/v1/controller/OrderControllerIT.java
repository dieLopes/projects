package com.diego.homebroker.api.v1.controller;

import com.diego.homebroker.api.v1.BaseIT;
import com.diego.homebroker.api.v1.dto.company.CompanyResponseDTO;
import com.diego.homebroker.api.v1.dto.order.OrderCreateDTO;
import com.diego.homebroker.api.v1.dto.order.OrderRandomCreateDTO;
import com.diego.homebroker.api.v1.dto.order.OrderResponseDTO;
import com.diego.homebroker.api.v1.dto.order.OrderResponseListDTO;
import com.diego.homebroker.api.v1.dto.order.OrderResponseRandomDTO;
import com.diego.homebroker.api.v1.dto.user.UserCreateDTO;
import com.diego.homebroker.api.v1.dto.user.UserResponseDTO;
import com.diego.homebroker.domain.CompanyStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static com.diego.homebroker.domain.CompanyStatus.ACTIVE;
import static com.diego.homebroker.domain.CompanyStatus.INACTIVE;
import static com.diego.homebroker.domain.OrderStatus.CANCELLED;
import static com.diego.homebroker.domain.OrderStatus.OK;
import static com.diego.homebroker.domain.OrderStatus.PENDING;
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
        String cpf = getUserCpf();
        createOrder("BUY", "BIDI11", 10, cpf);
        createOrder("SELL", "BIDI11", 5, cpf);
        createOrder("BUY", "LREN3", 5, cpf);

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
        String cpf = getUserCpf();
        OrderResponseDTO orderCreated = createOrder("BUY", "EGIE3", 10, cpf);

        OrderResponseDTO orderResponseDTO = restTemplate.getForObject(
                "http://localhost:" + port + ORDER_PATH + orderCreated.getId(), OrderResponseDTO.class);

        assertNotNull(orderResponseDTO.getDate());
        assertEquals(orderCreated.getId(), orderResponseDTO.getId());
        assertEquals(orderCreated.getType(), orderResponseDTO.getType());
        assertEquals(orderCreated.getAmount(), orderResponseDTO.getAmount());
        assertEquals(orderCreated.getUnitPrice(), orderResponseDTO.getUnitPrice());
        assertEquals(orderCreated.getTotalPrice(), orderResponseDTO.getTotalPrice());
        assertEquals(orderCreated.getCompanyCode(), orderResponseDTO.getCompanyCode());
        assertEquals(orderCreated.getCpf(), orderResponseDTO.getCpf());
    }

    @Test
    public void whenFindOrdersFilteringByCodeThenReturnOrderList() {
        String cpf = getUserCpf();
        createOrder("BUY", "BIDI11", 10, cpf);
        createOrder("SELL", "BIDI11", 5, cpf);
        createOrder("BUY", "LREN3", 5, cpf);
        OrderResponseListDTO orderResponseListDTO = restTemplate
                .getForObject("http://localhost:" + port + ORDER_PATH + "?code=BIDI11", OrderResponseListDTO.class);
        assertEquals(2, orderResponseListDTO.getOrders().size());
    }

    @Test
    public void whenCreateOrderThenReturnOrder() {
        String cpf = getUserCpf();
        OrderResponseDTO orderResponseDTO = createOrder("BUY", "BIDI11", 10, cpf);
        assertNotNull(orderResponseDTO.getId());
        assertNotNull(orderResponseDTO.getDate());
        assertEquals("BUY", orderResponseDTO.getType());
        assertEquals(10, orderResponseDTO.getAmount());
        assertEquals(new BigDecimal("66.51"), orderResponseDTO.getUnitPrice());
        assertEquals(new BigDecimal("665.10"), orderResponseDTO.getTotalPrice());
        assertEquals("BIDI11", orderResponseDTO.getCompanyCode());
        assertEquals("11111111111", orderResponseDTO.getCpf());
        assertEquals(PENDING.toString(), orderResponseDTO.getStatus());
    }

    @Test
    public void whenCreateOrderForInactiveCompanyThenReturnBadRequest() {
        getUserCpf();
        patchCompany("inter-id", INACTIVE);
        OrderCreateDTO orderCreateDTO = OrderCreateDTO.Builder.of()
                .cpf("11111111111")
                .code("BIDI11")
                .type("BUY")
                .amount(5)
                .build();
        ResponseEntity<OrderResponseDTO> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + ORDER_PATH, orderCreateDTO, OrderResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
        patchCompany("inter-id", ACTIVE);
    }

    @Test
    public void whenCreateWrongOrderAndGetThenReturnOrderWithCancelledStatus() throws InterruptedException {
        String cpf = getUserCpf();
        OrderResponseDTO orderResponseDTO = createOrder("SELL", "MGLU3", 10, cpf);
        assertEquals("PENDING", orderResponseDTO.getStatus());
        Thread.sleep(200);
        OrderResponseDTO updatedOrder = restTemplate.getForObject(
                "http://localhost:" + port + ORDER_PATH + orderResponseDTO.getId(), OrderResponseDTO.class);
        assertEquals(CANCELLED.toString(), updatedOrder.getStatus());
    }

    @Test
    public void whenCreateRightOrderAndGetThenReturnOrderWithOKStatus() throws InterruptedException {
        String cpf = getUserCpf();
        OrderResponseDTO orderResponseDTO = createOrder("BUY", "MGLU3", 10, cpf);
        assertEquals("PENDING", orderResponseDTO.getStatus());
        Thread.sleep(200);
        OrderResponseDTO updatedOrder = restTemplate.getForObject(
                "http://localhost:" + port + ORDER_PATH + orderResponseDTO.getId(), OrderResponseDTO.class);
        assertEquals(OK.toString(), updatedOrder.getStatus());
    }

    @Test
    public void whenCreateARandomOrderThenReturnOrders() {
        String cpf = getUserCpf();
        OrderRandomCreateDTO orderRandomCreateDTO = OrderRandomCreateDTO.Builder.of()
                .total(new BigDecimal(100))
                .cpf(cpf)
                .amount(3)
                .build();
        OrderResponseRandomDTO list = restTemplate.postForEntity(
                "http://localhost:" + port + ORDER_PATH + "/random",
                orderRandomCreateDTO, OrderResponseRandomDTO.class).getBody();
        assertNotNull(Objects.requireNonNull(list).getOrders());
        assertEquals(3, list.getOrders().size());
        assertEquals(new BigDecimal("98.24"), list.getTotal());
        assertEquals(new BigDecimal("1.76"), list.getChange());
    }

    @Test
    public void whenCreateARandomOrderIgnoreInactiveCompaniesAndThenReturnOrders() {
        patchCompany("marisa-id", INACTIVE);
        String cpf = getUserCpf();
        OrderRandomCreateDTO orderRandomCreateDTO = OrderRandomCreateDTO.Builder.of()
                .total(new BigDecimal(1000))
                .cpf(cpf)
                .amount(5)
                .build();
        OrderResponseRandomDTO list = restTemplate.postForEntity(
                "http://localhost:" + port + ORDER_PATH + "/random",
                orderRandomCreateDTO, OrderResponseRandomDTO.class).getBody();
        assertNotNull(Objects.requireNonNull(list).getOrders());
        assertEquals(5, list.getOrders().size());
        assertEquals(new BigDecimal("982.76"), list.getTotal());
        assertEquals(new BigDecimal("17.24"), list.getChange());
        patchCompany("marisa-id", ACTIVE);
    }

    @Test
    public void whenCreateARandomOrderInsufficientFundsAndThenReturnOrders() {
        String cpf = getUserCpf();
        OrderRandomCreateDTO orderRandomCreateDTO = OrderRandomCreateDTO.Builder.of()
                .total(new BigDecimal(10))
                .cpf(cpf)
                .amount(5)
                .build();
        OrderResponseRandomDTO list = restTemplate.postForEntity(
                "http://localhost:" + port + ORDER_PATH + "/random",
                orderRandomCreateDTO, OrderResponseRandomDTO.class).getBody();
        assertNotNull(Objects.requireNonNull(list).getOrders());
        assertEquals(1, list.getOrders().size());
        assertEquals(new BigDecimal("6.30"), list.getTotal());
        assertEquals(new BigDecimal("3.70"), list.getChange());
    }

    private OrderResponseDTO createOrder (String type, String code, int amount, String cpf) {
        OrderCreateDTO orderCreateDTO = OrderCreateDTO.Builder.of()
                .cpf(cpf)
                .code(code)
                .type(type)
                .amount(amount)
                .build();
        return restTemplate.postForEntity("http://localhost:" + port + ORDER_PATH, orderCreateDTO,
                OrderResponseDTO.class).getBody();
    }

    private String getUserCpf() {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("New User")
                .cpf("11111111111")
                .build();
        ResponseEntity<UserResponseDTO> user = restTemplate.postForEntity(
                "http://localhost:" + port + "/broker/api/v1/users", userCreateDTO, UserResponseDTO.class);
        return Objects.requireNonNull(user.getBody()).getCpf();
    }

    private void patchCompany (String id, CompanyStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(Map.of("status", status.toString()), headers);
        restTemplate.patchForObject("http://localhost:" + port + "/broker/api/v1/companies/" + id, entity,
                CompanyResponseDTO.class);
    }
}
