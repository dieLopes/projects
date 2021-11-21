package com.inter.desafiointer.builder;

import com.inter.desafiointer.domain.WalletStock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletStockBuilderTest {

    @Test
    public void whenBuildClassThenReturnClass () {
        WalletStock stock = WalletStockBuilder.of()
                .id("some-id")
                .amount(5)
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .balance(BigDecimal.TEN)
                .companyCode("SOME4")
                .build();
        assertThat(stock.getId()).isEqualTo("some-id");
        assertThat(stock.getAmount()).isEqualTo(5);
        assertThat(stock.getWallet().getId()).isEqualTo("wallet-id");
        assertThat(stock.getBalance()).isEqualTo(BigDecimal.TEN);
        assertThat(stock.getCompanyCode()).isEqualTo("SOME4");
    }
}
