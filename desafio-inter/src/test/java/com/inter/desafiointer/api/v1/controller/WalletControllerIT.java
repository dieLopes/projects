package com.inter.desafiointer.api.v1.controller;

import com.inter.desafiointer.api.v1.BaseIT;
import com.inter.desafiointer.api.v1.dto.order.OrderCreateDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderRandomCreateDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseListDTO;
import com.inter.desafiointer.api.v1.dto.order.OrderResponseRandomDTO;
import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseDTO;
import com.inter.desafiointer.api.v1.dto.wallet.WalletResponseDTO;
import com.inter.desafiointer.api.v1.dto.walletstock.WalletStockResponseDTO;
import com.inter.desafiointer.api.v1.dto.walletstock.WalletStockResponseListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class WalletControllerIT extends BaseIT {

    private final String WALLET_PATH = "/broker/api/v1/wallets/";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenFindWalletByUserCpfThenReturnWallet() {
        UserResponseDTO user = getUser();
        WalletResponseDTO walletResponseDTO = restTemplate.getForObject(
                "http://localhost:" + port + WALLET_PATH + user.getCpf(), WalletResponseDTO.class);
        assertNotNull(walletResponseDTO.getId());
        assertEquals(new BigDecimal("0.00"), walletResponseDTO.getBalance());
    }

    @Test
    public void whenFindWalletByInvalidIdThenReturnNotFound() {
        ResponseEntity<WalletResponseDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + WALLET_PATH + 222L, WalletResponseDTO.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenFindAllWalletOrdersThenReturnOrderList() {
        String cpf = getUser().getCpf();
        createOrder("BUY", "BIDI11", 10, cpf);
        createOrder("SELL", "BIDI11", 5, cpf);
        createOrder("BUY", "LREN3", 5, cpf);

        OrderResponseListDTO orderResponseListDTO = restTemplate.getForObject(
                "http://localhost:" + port + WALLET_PATH + cpf + "/orders", OrderResponseListDTO.class);
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
    public void whenFindAllWalletStocksThenReturnStockList() throws InterruptedException {
        String cpf = getUser().getCpf();
        createOrder("BUY", "BIDI11", 10, cpf);
        Thread.sleep(1000);
        createOrder("SELL", "BIDI11", 5, cpf);
        createOrder("BUY", "LREN3", 5, cpf);

        WalletStockResponseListDTO walletStockResponseListDTO = restTemplate.getForObject(
                "http://localhost:" + port + WALLET_PATH + cpf + "/stocks", WalletStockResponseListDTO.class);
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

    @Test
    public void whenFindAllWalletStocksRandomBuyThenReturnStockList() throws InterruptedException {
        String cpf = getUser().getCpf();
        OrderRandomCreateDTO orderRandomCreateDTO = OrderRandomCreateDTO.Builder.of()
                .total(new BigDecimal(100))
                .cpf(cpf)
                .amount(4)
                .build();
        restTemplate.postForEntity("http://localhost:" + port + "/broker/api/v1/orders/random",
                orderRandomCreateDTO, OrderResponseRandomDTO.class).getBody();
        Thread.sleep(3000);

        WalletStockResponseListDTO walletStockResponseListDTO = restTemplate.getForObject(
                "http://localhost:" + port + WALLET_PATH + cpf + "/stocks", WalletStockResponseListDTO.class);
        assertEquals(4, walletStockResponseListDTO.getStocks().size());

        validateStock("AMAR3", 2).accept(walletStockResponseListDTO);
        validateStock("MGLU3", 2).accept(walletStockResponseListDTO);
        validateStock("CVCB3", 1).accept(walletStockResponseListDTO);
        validateStock("SULA11", 1).accept(walletStockResponseListDTO);
    }

    private Consumer<WalletStockResponseListDTO> validateStock (String code, int amount) {
        return opt -> {
            Optional<WalletStockResponseDTO> optStock = opt.getStocks()
                    .stream()
                    .filter(stock -> stock.getCompanyCode().equals(code))
                    .findFirst();
            assertTrue(optStock.isPresent());
            assertEquals(code, optStock.get().getCompanyCode());
            assertEquals(amount, optStock.get().getAmount());
        };
    }

    @Test
    public void whenBuyAndSellStocksThenReturnWalletBalanceUpdated() throws InterruptedException {
        String cpf = getUser().getCpf();
        WalletResponseDTO walletResponseDTO = restTemplate
                .getForObject("http://localhost:" + port + WALLET_PATH + cpf, WalletResponseDTO.class);
        assertNotNull(walletResponseDTO.getId());
        assertEquals(new BigDecimal("0.00"), walletResponseDTO.getBalance());

        createOrder("BUY", "BIDI11", 10, cpf);
        Thread.sleep(1000);
        walletResponseDTO = restTemplate.getForObject(
                "http://localhost:" + port + WALLET_PATH + cpf, WalletResponseDTO.class);
        assertEquals(new BigDecimal("665.10"), walletResponseDTO.getBalance());

        createOrder("SELL", "BIDI11", 4, cpf);
        Thread.sleep(1000);
        walletResponseDTO = restTemplate.getForObject(
                "http://localhost:" + port + WALLET_PATH + cpf, WalletResponseDTO.class);
        assertEquals(new BigDecimal("399.06"), walletResponseDTO.getBalance());
    }

    private void createOrder (String type, String code, int amount, String cpf) {
        OrderCreateDTO orderCreateDTO = OrderCreateDTO.Builder.of()
                .cpf(cpf)
                .code(code)
                .type(type)
                .amount(amount)
                .build();
        restTemplate.postForEntity("http://localhost:" + port + "/broker/api/v1/orders/", orderCreateDTO,
                OrderResponseDTO.class);
    }

    public UserResponseDTO getUser() {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("New User")
                .cpf(String.valueOf(System.currentTimeMillis()))
                .build();
        ResponseEntity<UserResponseDTO> user = restTemplate.postForEntity(
                "http://localhost:" + port + "/broker/api/v1/users", userCreateDTO, UserResponseDTO.class);
        return Objects.requireNonNull(user.getBody());
    }
}
