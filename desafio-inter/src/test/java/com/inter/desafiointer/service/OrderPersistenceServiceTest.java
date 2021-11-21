package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.CompanyBuilder;
import com.inter.desafiointer.builder.OrderBuilder;
import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.domain.Order;
import com.inter.desafiointer.domain.Wallet;
import com.inter.desafiointer.exception.BadRequestException;
import com.inter.desafiointer.repository.CompanyRepository;
import com.inter.desafiointer.repository.OrderRepository;
import com.inter.desafiointer.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.inter.desafiointer.domain.CompanyStatus.ACTIVE;
import static com.inter.desafiointer.domain.OrderType.BUY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderPersistenceServiceTest {

    @InjectMocks
    private OrderPersistenceService orderPersistenceService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private WalletStockPersistenceService walletStockPersistenceService;

    private static final List<Company> companies = new ArrayList<>();
    static {
        companies.add(buildCompany("marisa-id", "Marisa", "AMAR3", new BigDecimal("6.30")));
        companies.add(buildCompany("magalu-id", "Magazine Luiza", "MGLU3", new BigDecimal("18.80")));
        companies.add(buildCompany("cvc-id", "CVC", "CVCB3", new BigDecimal("20.87")));
        companies.add(buildCompany("sulamerica-id", "Sulamérica", "SULA11", new BigDecimal("28.26")));
        companies.add(buildCompany("renner-id", "Renner", "LREN3", new BigDecimal("36.95")));
        companies.add(buildCompany("engie-id", "Engie", "EGIE3", new BigDecimal("38.30")));
        companies.add(buildCompany("inter-id", "Inter", "BIDI11", new BigDecimal("66.51")));
    }

    @Test
    public void whenCreateOrderThenSaveOrder () throws InterruptedException {
        Order order = buildOrder(5);
        when(companyRepository.findByCodeAndStatus(eq("SOME4"), eq(ACTIVE)))
                .thenReturn(Optional.of(buildCompany()));
        when(walletRepository.findByUserCpf(eq("111"))).thenReturn(Optional.of(buildWallet()));
        when(orderRepository.save(eq(order))).thenReturn(order);
        when(walletStockPersistenceService.processOrder(anyList()))
                .thenReturn(CompletableFuture.completedFuture(List.of(order)));
        orderPersistenceService.save(order);
        Thread.sleep(500);
        verify(companyRepository).findByCodeAndStatus(eq("SOME4"), eq(ACTIVE));
        verify(walletRepository).findByUserCpf(eq("111"));
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(walletStockPersistenceService).processOrder(anyList());
        verifyNoMoreInteractions(companyRepository, walletRepository, orderRepository, walletStockPersistenceService);
    }

    @Test
    public void whenCreateOrderWithInvalidAmountThenReturnBadRequest () {
        Order order = buildOrder(0);
        assertThatThrownBy(() -> orderPersistenceService.save(order))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Amount should be greater than 0");
        verifyZeroInteractions(companyRepository, walletRepository, orderRepository, walletStockPersistenceService);
    }

    @Test
    public void whenCreateOrderWithNullCodeThenReturnBadRequest () {
        Order order = buildOrder(5);
        order.setCode(null);
        assertThatThrownBy(() -> orderPersistenceService.save(order))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Code is invalid");
        verifyZeroInteractions(companyRepository, walletRepository, orderRepository, walletStockPersistenceService);
    }

    @Test
    public void whenCreateOrderWithInvalidCompanyThenReturnBadRequest () {
        Order order = buildOrder(5);
        when(companyRepository.findByCodeAndStatus(eq("SOME4"), eq(ACTIVE)))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderPersistenceService.save(order))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Company not found with code " + order.getCode());
        verify(companyRepository).findByCodeAndStatus(eq("SOME4"), eq(ACTIVE));
        verifyNoMoreInteractions(companyRepository);
        verifyZeroInteractions(walletRepository, walletStockPersistenceService, orderRepository);
    }

    @Test
    public void whenCreateOrderInvalidWalletThenReturnBadRequest () {
        Order order = buildOrder(5);
        when(companyRepository.findByCodeAndStatus(eq("SOME4"), eq(ACTIVE)))
                .thenReturn(Optional.of(buildCompany()));
        when(walletRepository.findByUserCpf(eq("111"))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderPersistenceService.save(order))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Wallet not found for user 111");
        verify(companyRepository).findByCodeAndStatus(eq("SOME4"), eq(ACTIVE));
        verify(walletRepository).findByUserCpf(eq("111"));
        verifyNoMoreInteractions(companyRepository, walletRepository);
        verifyZeroInteractions(walletStockPersistenceService, orderRepository);
    }

    @Test
    public void whenCreateRandomOrdersThenSaveAListOfOrders () throws InterruptedException {
        when(walletRepository.findByUserCpf(eq("11111111111"))).thenReturn(Optional.of(buildWallet()));
        when(companyRepository.findByStatusOrderByPriceAsc(eq(ACTIVE))).thenReturn(companies);
        List<Order> orders = new ArrayList<>();
        while (orders.size() < 4) {
            orders.add(buildOrder(5));
        }
        when(walletStockPersistenceService.processOrder(anyList()))
                .thenReturn(CompletableFuture.completedFuture(orders));
        orderPersistenceService.createRandomOrders("11111111111", new BigDecimal("100.00"));
        Thread.sleep(500);
        verify(walletRepository).findByUserCpf(eq("11111111111"));
        verify(companyRepository).findByStatusOrderByPriceAsc(eq(ACTIVE));
        verify(orderRepository, times(8)).save(any(Order.class));
        verify(walletStockPersistenceService).processOrder(anyList());
        verifyNoMoreInteractions(walletRepository, companyRepository, orderRepository, walletStockPersistenceService);
    }

    @Test
    public void whenCreateRandomOrderButInactiveCompaniesThenReturnBadRequest () {
        when(walletRepository.findByUserCpf(eq("11111111111"))).thenReturn(Optional.of(buildWallet()));
        when(companyRepository.findByStatusOrderByPriceAsc(eq(ACTIVE))).thenReturn(List.of());
        assertThatThrownBy(() -> orderPersistenceService.createRandomOrders("11111111111", BigDecimal.TEN))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("There aren't companies to buy");
        verify(walletRepository).findByUserCpf(eq("11111111111"));
        verify(companyRepository).findByStatusOrderByPriceAsc(eq(ACTIVE));
        verifyNoMoreInteractions(walletRepository, companyRepository);
        verifyZeroInteractions(walletStockPersistenceService, orderRepository);
    }

    @Test
    public void whenCreateRandomOrderInvalidWalletThenReturnBadRequest () {
        when(walletRepository.findByUserCpf(eq("11111111111"))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderPersistenceService.createRandomOrders("11111111111", BigDecimal.TEN))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Wallet not found for user 11111111111");
        verify(walletRepository).findByUserCpf(eq("11111111111"));
        verifyNoMoreInteractions(walletRepository);
        verifyZeroInteractions(companyRepository, walletStockPersistenceService, orderRepository);
    }

    private Order buildOrder(int amount) {
        return OrderBuilder.of()
                .amount(amount)
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .code("SOME4")
                .cpf("111")
                .type(BUY)
                .build();
    }

    private Company buildCompany () {
        return buildCompany("company-id", "some name", "SOME4", BigDecimal.TEN);
    }

    private static Company buildCompany (String id, String name, String code, BigDecimal price) {
        return CompanyBuilder.of()
                .id(id)
                .name(name)
                .code(code)
                .price(price)
                .build();
    }

    private Wallet buildWallet () {
        return WalletBuilder.of()
                .id("wallet-id")
                .build();
    }
}