package com.diego.homebroker.builder;

import com.diego.homebroker.domain.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.diego.homebroker.domain.OrderStatus.PENDING;
import static com.diego.homebroker.domain.OrderType.BUY;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderBuilderTest {

    @Test
    public void whenBuildClassThenReturnClass () {
        LocalDateTime date = LocalDateTime.now();
        Order order = OrderBuilder.of()
                .id("some-id")
                .amount(5)
                .company(CompanyBuilder.of()
                        .code("SOME4")
                        .build())
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .status(PENDING)
                .date(date)
                .type(BUY)
                .unitPrice(new BigDecimal(10))
                .totalPrice(new BigDecimal(50))
                .code("SOME4")
                .build();
        assertThat(order.getId()).isEqualTo("some-id");
        assertThat(order.getAmount()).isEqualTo(5);
        assertThat(order.getCompany().getCode()).isEqualTo("SOME4");
        assertThat(order.getWallet().getId()).isEqualTo("wallet-id");
        assertThat(order.getStatus()).isEqualTo(PENDING);
        assertThat(order.getDate()).isEqualTo(date);
        assertThat(order.getType()).isEqualTo(BUY);
        assertThat(order.getUnitPrice()).isEqualTo(new BigDecimal(10));
        assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal(50));
        assertThat(order.getCode()).isEqualTo("SOME4");
    }
}
