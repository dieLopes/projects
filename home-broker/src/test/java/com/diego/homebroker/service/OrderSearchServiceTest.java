package com.diego.homebroker.service;

import com.diego.homebroker.builder.CompanyBuilder;
import com.diego.homebroker.builder.OrderBuilder;
import com.diego.homebroker.builder.WalletBuilder;
import com.diego.homebroker.domain.Order;
import com.diego.homebroker.exception.NotFoundException;
import com.diego.homebroker.repository.OrderRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderSearchServiceTest {

    @InjectMocks
    private OrderSearchService orderSearchService;
    @Mock
    private OrderRepository orderRepository;

    @Test
    public void whenFindOrdersThenReturnOrders () {
        List<Order> orders = List.of(
                buildOrder("some-id"),
                buildOrder("another-id"));
        when(orderRepository.find("SOME3")).thenReturn(orders);
        assertThat(orderSearchService.find("SOME3")).hasSize(2)
                .extracting(Order::getId)
                .containsExactlyInAnyOrder("some-id","another-id");
        verify(orderRepository).find(eq("SOME3"));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void whenFindOrdersWithInvalidCodeThenReturnEmptyList () {
        when(orderRepository.find("SOME3")).thenReturn(List.of());
        assertThat(orderSearchService.find("SOME3")).isEmpty();
        verify(orderRepository).find(eq("SOME3"));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void whenFindOrderByIdThenReturnOrder () throws NotFoundException {
        Order order = buildOrder("some-id");
        when(orderRepository.findById(eq("some-id"))).thenReturn(Optional.of(order));
        assertThat(orderSearchService.findById("some-id")).satisfies(find ->
                assertThat(find.getId()).isEqualTo(order.getId()));
        verify(orderRepository).findById(eq("some-id"));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void whenFindOrderByInvalidIdThenReturnNotFound () {
        when(orderRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderSearchService.findById("INVALID3"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order not found");
        verify(orderRepository).findById(eq("INVALID3"));
        verifyNoMoreInteractions(orderRepository);
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
}
