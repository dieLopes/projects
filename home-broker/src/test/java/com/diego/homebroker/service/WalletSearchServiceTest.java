package com.diego.homebroker.service;

import com.diego.homebroker.builder.WalletStockBuilder;
import com.diego.homebroker.domain.WalletStock;
import com.diego.homebroker.repository.WalletStockRepository;
import com.diego.homebroker.builder.CompanyBuilder;
import com.diego.homebroker.builder.OrderBuilder;
import com.diego.homebroker.builder.UserBuilder;
import com.diego.homebroker.builder.WalletBuilder;
import com.diego.homebroker.domain.Order;
import com.diego.homebroker.domain.User;
import com.diego.homebroker.domain.Wallet;
import com.diego.homebroker.exception.NotFoundException;
import com.diego.homebroker.repository.OrderRepository;
import com.diego.homebroker.repository.UserRepository;
import com.diego.homebroker.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.diego.homebroker.domain.OrderStatus.PENDING;
import static com.diego.homebroker.domain.OrderType.BUY;
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
    @Mock
    private UserRepository userRepository;

    @Test
    public void whenFindWalletByIdThenReturnWalletWithBalance () {
        when(userRepository.findByCpf(eq("111"))).thenReturn(Optional.of(buildUser()));
        when(walletStockRepository.findWalletBalance(any(Wallet.class))).thenReturn(BigDecimal.TEN);
        assertThat(walletSearchService.findByUserCpf("111")).satisfies(find -> {
            assertThat(find.getId()).isEqualTo("wallet-id");
            assertThat(find.getBalance()).isEqualTo(BigDecimal.TEN);
        });
        verify(userRepository).findByCpf(eq("111"));
        verify(walletStockRepository).findWalletBalance(any(Wallet.class));
        verifyNoMoreInteractions(walletRepository);
    }

    @Test
    public void whenFindUserWalletInvalidCpfThenReturnNotFound () {
        when(userRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> walletSearchService.findByUserCpf("111"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Wallet not found");
        verify(userRepository).findByCpf(eq("111"));
        verifyNoMoreInteractions(walletRepository);
        verifyZeroInteractions(walletStockRepository);
    }

    @Test
    public void whenFindWalletOrdersThenReturnOrdersInWallet () {
        List<Order> orders = List.of(
                buildOrder("some-id"),
                buildOrder("another-id"));
        when(userRepository.findByCpf(eq("111"))).thenReturn(Optional.of(buildUser()));
        when(orderRepository.findByWallet(any(Wallet.class))).thenReturn(orders);
        assertThat(walletSearchService.findOrders("111")).hasSize(2)
                .extracting(Order::getId)
                .containsExactlyInAnyOrder("some-id", "another-id");
        verify(userRepository).findByCpf(eq("111"));
        verify(orderRepository).findByWallet(any(Wallet.class));
        verifyNoMoreInteractions(userRepository, orderRepository);
        verifyZeroInteractions(walletRepository);
    }

    @Test
    public void whenFindWalletOrdersWithInvalidCpfThenReturnNotFound () {
        when(userRepository.findByCpf(eq("111"))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> walletSearchService.findOrders("111"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
        verify(userRepository).findByCpf(eq("111"));
        verifyNoMoreInteractions(userRepository);
        verifyZeroInteractions(walletRepository, orderRepository, walletStockRepository);
    }

    @Test
    public void whenFindWalletStocksThenReturnStocksInWallet () {
        User user = buildUser();
        List<WalletStock> stocks = List.of(
                buildStock("some-id"),
                buildStock("another-id"));
        when(userRepository.findByCpf(eq("111"))).thenReturn(Optional.of(user));
        when(walletStockRepository.findByWallet(any(Wallet.class))).thenReturn(stocks);
        assertThat(walletSearchService.findStocks("111")).hasSize(2)
                .extracting(WalletStock::getId)
                .containsExactlyInAnyOrder("some-id", "another-id");
        verify(userRepository).findByCpf(eq("111"));
        verify(walletStockRepository).findByWallet(any(Wallet.class));
        verifyNoMoreInteractions(userRepository, walletStockRepository);
        verifyZeroInteractions(walletRepository, orderRepository);
    }

    @Test
    public void whenFindWalletStocksWithInvalidCpfThenReturnNotFound () {
        when(userRepository.findByCpf(eq("111"))).thenReturn(Optional.empty());
        assertThatThrownBy(() -> walletSearchService.findStocks("111"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
        verify(userRepository).findByCpf(eq("111"));
        verifyNoMoreInteractions(userRepository);
        verifyZeroInteractions(walletRepository, orderRepository, walletStockRepository);
    }

    private User buildUser() {
        return UserBuilder.of()
                .id("some-id")
                .cpf("111")
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
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
