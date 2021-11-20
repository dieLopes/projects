package com.inter.desafiointer.api.v1.controller;

import com.inter.desafiointer.api.v1.BaseIT;
import com.inter.desafiointer.api.v1.dto.order.OrderCreateDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseListDTO;
import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseDTO;
import com.inter.desafiointer.api.v1.dto.wallet.WalletResponseDTO;
import com.inter.desafiointer.api.v1.dto.walletstock.WalletStockResponseDTO;
import com.inter.desafiointer.api.v1.dto.walletstock.WalletStockResponseListDTO;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerIT extends BaseIT {

    private final String WALLET_PATH = "/broker/api/v1/wallets/";
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
    public void whenFindWalletByIdThenReturnWallet() {
        WalletResponseDTO walletResponseDTO = restTemplate
                .getForObject("http://localhost:" + port + WALLET_PATH + wallet.getId(), WalletResponseDTO.class);
        assertNotNull(walletResponseDTO.getId());
        assertEquals(new BigDecimal("0.00"), walletResponseDTO.getBalance());
    }

    @Test
    @Order(2)
    public void whenFindWalletByInvalidIdThenReturnNotFound() {
        ResponseEntity<WalletResponseDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + WALLET_PATH + "INVALID-ID", WalletResponseDTO.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    @Order(3)
    public void whenFindAllWalletOrdersThenReturnOrderList() throws InterruptedException {

        createOrder("BUY", "BIDI11", 10);
        Thread.sleep(2000);
        createOrder("SELL", "BIDI11", 5);
        createOrder("BUY", "LREN3", 5);

        OrderResponseListDTO orderResponseListDTO = restTemplate.getForObject(
                "http://localhost:" + port + WALLET_PATH + wallet.getId() + "/orders", OrderResponseListDTO.class);
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
    @Order(4)
    public void whenFindAllWalletStocksThenReturnStockList() {

        WalletStockResponseListDTO walletStockResponseListDTO = restTemplate.getForObject(
                "http://localhost:" + port + WALLET_PATH + wallet.getId() + "/stocks", WalletStockResponseListDTO.class);
        assertEquals(2, walletStockResponseListDTO.getStocks().size());

        Optional<WalletStockResponseDTO> optBidi = walletStockResponseListDTO.getStocks()
                .stream()
                .filter(stock -> stock.getCompanyCode().equals("BIDI11"))
                .findFirst();
        assertTrue(optBidi.isPresent());
        assertNotNull(optBidi.get().getId());
        assertEquals("BIDI11", optBidi.get().getCompanyCode());
        assertEquals(5, optBidi.get().getAmount());
        assertEquals(new BigDecimal("332.55"), optBidi.get().getBalance());
        assertEquals(new BigDecimal("66.51"), optBidi.get().getAveragePrice());

        Optional<WalletStockResponseDTO> optLren = walletStockResponseListDTO.getStocks()
                .stream()
                .filter(stock -> stock.getCompanyCode().equals("LREN3"))
                .findFirst();
        assertTrue(optLren.isPresent());
        assertNotNull(optLren.get().getId());
        assertEquals("LREN3", optLren.get().getCompanyCode());
        assertEquals(5, optLren.get().getAmount());
        assertEquals(new BigDecimal("184.75"), optLren.get().getBalance());
        assertEquals(new BigDecimal("36.95"), optLren.get().getAveragePrice());
    }

    private void createOrder (String type, String code, int amount) {
        OrderCreateDTO orderCreateDTO = OrderCreateDTO.Builder.of()
                .walletId(wallet.getId())
                .code(code)
                .type(type)
                .amount(amount)
                .build();
        restTemplate.postForEntity("http://localhost:" + port + "/broker/api/v1/orders/", orderCreateDTO,
                OrderResponseDTO.class);
    }
}
