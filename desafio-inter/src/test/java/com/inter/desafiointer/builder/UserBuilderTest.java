package com.inter.desafiointer.builder;

import com.inter.desafiointer.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserBuilderTest {

    @Test
    public void whenBuildClassThenReturnClass () {
        User user = UserBuilder.of()
                .id("some-id")
                .name("Some Name")
                .cpf("11111111111")
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .build();
        assertThat(user.getId()).isEqualTo("some-id");
        assertThat(user.getName()).isEqualTo("Some Name");
        assertThat(user.getCpf()).isEqualTo("11111111111");
        assertThat(user.getWallet().getId()).isEqualTo("wallet-id");
    }
}
