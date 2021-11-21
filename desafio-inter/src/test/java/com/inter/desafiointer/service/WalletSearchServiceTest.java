package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.CompanyBuilder;
import com.inter.desafiointer.builder.OrderBuilder;
import com.inter.desafiointer.builder.UserBuilder;
import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.builder.WalletStockBuilder;
import com.inter.desafiointer.domain.Order;
import com.inter.desafiointer.domain.Wallet;
import com.inter.desafiointer.domain.WalletStock;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.OrderRepository;
import com.inter.desafiointer.repository.WalletRepository;
import com.inter.desafiointer.repository.WalletStockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.inter.desafiointer.domain.OrderStatus.PENDING;
import static com.inter.desafiointer.domain.OrderType.BUY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletSearchServiceTest {

    @InjectMocks
    private WalletSearchService walletSearchService;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private WalletStockRepository walletStockRepository;
    @Mock
    private OrderRepository orderRepository;

    @Test
    public void whenFindWalletByIdThenReturnWalletWithBalance () throws NotFoundException {
        Wallet wallet = buildWallet("some-id");
        when(walletRepository.findById(eq("some-id"))).thenReturn(Optional.of(wallet));
        when(walletStockRepository.findWalletBalance(wallet)).thenReturn(BigDecimal.TEN);
        assertThat(walletSearchService.findById("some-id")).satisfies(find -> {
            assertThat(find.getId()).isEqualTo(wallet.getId());
            assertThat(find.getBalance()).isEqualTo(BigDecimal.TEN);
        });
        verify(walletRepository).findById(eq("some-id"));
        verify(walletStockRepository).findWalletBalance(eq(wallet));
        verifyNoMoreInteractions(walletRepository);
    }

    @Test
    public void whenFindWalletByInvalidIdThenReturnNotFound () {
        when(walletRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> walletSearchService.findById("INVALID3"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Wallet not found");
        verify(walletRepository).findById(eq("INVALID3"));
        verifyNoMoreInteractions(walletRepository);
        verifyZeroInteractions(walletStockRepository);
    }

    @Test
    public void whenFindWalletOrdersThenReturnOrdersInWallet () {
        Wallet wallet = buildWallet("wallet-id");
        List<Order> orders = List.of(
                buildOrder("some-id"),
                buildOrder("another-id"));
        when(walletRepository.findById(eq("wallet-id"))).thenReturn(Optional.of(wallet));
        when(orderRepository.findByWallet(any(Wallet.class))).thenReturn(orders);
        when(walletStockRepository.findWalletBalance(wallet)).thenReturn(BigDecimal.TEN);
        assertThat(walletSearchService.findOrders("wallet-id")).hasSize(2)
                .extracting(Order::getId)
                .containsExactlyInAnyOrder("some-id", "another-id");
        verify(walletRepository).findById(eq("wallet-id"));
        verify(orderRepository).findByWallet(any(Wallet.class));
        verify(walletStockRepository).findWalletBalance(eq(wallet));
        verifyNoMoreInteractions(walletRepository, orderRepository);
    }

    @Test
    public void whenFindWalletOrdersWithInvalidIdThenReturnNotFound () {
        when(walletRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> walletSearchService.findOrders("INVALID3"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Wallet not found");
        verify(walletRepository).findById(eq("INVALID3"));
        verifyNoMoreInteractions(walletRepository);
        verifyZeroInteractions(orderRepository, walletStockRepository);
    }

    @Test
    public void whenFindWalletStocksThenReturnStocksInWallet () {
        Wallet wallet = buildWallet("wallet-id");
        List<WalletStock> stocks = List.of(
                buildStock("some-id"),
                buildStock("another-id"));
        when(walletRepository.findById(eq("wallet-id"))).thenReturn(Optional.of(wallet));
        when(walletStockRepository.findByWallet(any(Wallet.class))).thenReturn(stocks);
        when(walletStockRepository.findWalletBalance(wallet)).thenReturn(BigDecimal.TEN);
        assertThat(walletSearchService.findStocks("wallet-id")).hasSize(2)
                .extracting(WalletStock::getId)
                .containsExactlyInAnyOrder("some-id", "another-id");
        verify(walletRepository).findById(eq("wallet-id"));
        verify(walletStockRepository).findByWallet(any(Wallet.class));
        verify(walletStockRepository).findWalletBalance(eq(wallet));
        verifyNoMoreInteractions(walletRepository, walletStockRepository);
        verifyZeroInteractions(orderRepository);
    }

    @Test
    public void whenFindWalletStocksWithInvalidIdThenReturnNotFound () {
        when(walletRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> walletSearchService.findStocks("INVALID3"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Wallet not found");
        verify(walletRepository).findById(eq("INVALID3"));
        verifyNoMoreInteractions(walletRepository);
        verifyZeroInteractions(orderRepository, walletStockRepository);
    }

    private Wallet buildWallet (String id) {
        return WalletBuilder.of()
                .id(id)
                .user(UserBuilder.of()
                        .id("user-id")
                        .build())
                .build();
    }

    private Order buildOrder(String id) {
        return OrderBuilder.of()
                .id(id)
                .amount(5)
                .company(CompanyBuilder.of()
                        .code("SOME4")
                        .build())
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .status(PENDING)
                .date(LocalDateTime.now())
                .type(BUY)
                .unitPrice(new BigDecimal(10))
                .totalPrice(new BigDecimal(50))
                .build();
    }

    private WalletStock buildStock (String id) {
        return WalletStockBuilder.of()
                .id(id)
                .amount(5)
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .balance(BigDecimal.TEN)
                .companyCode("SOME4")
                .build();
    }
}
