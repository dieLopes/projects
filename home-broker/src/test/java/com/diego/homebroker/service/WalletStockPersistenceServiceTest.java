package com.diego.homebroker.service;

import com.diego.homebroker.builder.CompanyBuilder;
import com.diego.homebroker.builder.WalletBuilder;
import com.diego.homebroker.builder.WalletStockBuilder;
import com.diego.homebroker.domain.WalletStock;
import com.diego.homebroker.repository.WalletStockRepository;
import com.diego.homebroker.builder.OrderBuilder;
import com.diego.homebroker.domain.Order;
import com.diego.homebroker.domain.OrderType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.diego.homebroker.domain.OrderType.BUY;
import static com.diego.homebroker.domain.OrderType.SELL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletStockPersistenceServiceTest {

    @Spy
    @InjectMocks
    private WalletStockPersistenceService walletStockPersistenceService;
    @Mock
    private WalletStockRepository walletStockRepository;

    @Test
    public void whenCreateWalletStockThenSaveStock () {
        WalletStock stock = buildStock();
        walletStockPersistenceService.save(stock);
        verify(walletStockRepository).save(any(WalletStock.class));
        verifyNoMoreInteractions(walletStockRepository);
    }

    @Test
    public void whenUpdateWalletStockThenSaveStock () {
        WalletStock stock = buildStock();
        walletStockPersistenceService.update(stock);
        verify(walletStockRepository).save(any(WalletStock.class));
        verifyNoMoreInteractions(walletStockRepository);
    }

    @Test
    public void whenBuyOneStockThereIsNtInWalletThenAddStock () {
        Order order = buildOrder(5, BUY);
        when(walletStockRepository.findByCompanyCodeAndWalletId(eq("SOME4"), eq("wallet-id")))
                .thenReturn(Optional.empty());
        walletStockPersistenceService.processOrder(List.of(order));
        verify(walletStockRepository).findByCompanyCodeAndWalletId(eq("SOME4"), eq("wallet-id"));
        verify(walletStockPersistenceService).save(any(WalletStock.class));
        verify(walletStockRepository).save(any(WalletStock.class));
        verifyNoMoreInteractions(walletStockRepository);
    }

    @Test
    public void whenBuyOneStockThereIsInWalletThenUpdateStock () {
        Order order = buildOrder(4, BUY);
        when(walletStockRepository.findByCompanyCodeAndWalletId("SOME4", "wallet-id"))
                .thenReturn(Optional.of(buildStock()));
        walletStockPersistenceService.processOrder(List.of(order));
        verify(walletStockRepository).findByCompanyCodeAndWalletId(eq("SOME4"), eq("wallet-id"));
        verify(walletStockPersistenceService).update(any(WalletStock.class));
        verify(walletStockRepository).save(any(WalletStock.class));
        verifyNoMoreInteractions(walletStockRepository);
    }

    @Test
    public void whenSellOneStockThereIsNtInWalletThenCancelOrder () {
        Order order = buildOrder(5, SELL);
        when(walletStockRepository.findByCompanyCodeAndWalletId(eq("SOME4"), eq("wallet-id")))
                .thenReturn(Optional.empty());
        walletStockPersistenceService.processOrder(List.of(order));
        verify(walletStockRepository).findByCompanyCodeAndWalletId(eq("SOME4"), eq("wallet-id"));
        verifyNoMoreInteractions(walletStockRepository);
    }

    @Test
    public void whenSellAmountStockGreaterThanThereAreInWalletThenCancelOrder () {
        Order order = buildOrder(10, SELL);
        when(walletStockRepository.findByCompanyCodeAndWalletId(eq("SOME4"), eq("wallet-id")))
                .thenReturn(Optional.of(buildStock()));
        walletStockPersistenceService.processOrder(List.of(order));
        verify(walletStockRepository).findByCompanyCodeAndWalletId(eq("SOME4"), eq("wallet-id"));
        verifyNoMoreInteractions(walletStockRepository);
    }

    @Test
    public void whenSellAmountStockThenUpdateWallet () {
        Order order = buildOrder(5, SELL);
        when(walletStockRepository.findByCompanyCodeAndWalletId(eq("SOME4"), eq("wallet-id")))
                .thenReturn(Optional.of(buildStock()));
        walletStockPersistenceService.processOrder(List.of(order));
        verify(walletStockRepository).findByCompanyCodeAndWalletId(eq("SOME4"), eq("wallet-id"));
        verify(walletStockPersistenceService).update(any(WalletStock.class));
        verify(walletStockRepository).save(any(WalletStock.class));
        verifyNoMoreInteractions(walletStockRepository);
    }

    private WalletStock buildStock () {
        return WalletStockBuilder.of()
                .id("some-id")
                .amount(5)
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .balance(BigDecimal.TEN)
                .companyCode("SOME4")
                .build();
    }

    private Order buildOrder(int amount, OrderType type) {
        return OrderBuilder.of()
                .amount(amount)
                .company(CompanyBuilder.of()
                        .code("SOME4")
                        .build())
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .type(type)
                .unitPrice(BigDecimal.TEN)
                .totalPrice(BigDecimal.TEN.multiply(new BigDecimal(amount)))
                .build();
    }
}
