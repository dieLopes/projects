package com.diego.homebroker.builder;

import com.diego.homebroker.domain.Wallet;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletBuilderTest {

    @Test
    public void whenBuildClassThenReturnClass () {
        Wallet wallet = WalletBuilder.of()
                .id("some-id")
                .balance(BigDecimal.TEN)
                .user(UserBuilder.of()
                        .id("user-id")
                        .build())
                .build();
        assertThat(wallet.getId()).isEqualTo("some-id");
        assertThat(wallet.getBalance()).isEqualTo(BigDecimal.TEN);
        assertThat(wallet.getUser().getId()).isEqualTo("user-id");
    }
}
