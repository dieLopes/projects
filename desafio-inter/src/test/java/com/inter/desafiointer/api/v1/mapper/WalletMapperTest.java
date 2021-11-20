package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.wallet.WalletResponseDTO;
import com.inter.desafiointer.builder.UserBuilder;
import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.domain.Wallet;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletMapperTest {

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        Wallet wallet = WalletBuilder.of()
                .id("some-id")
                .balance(BigDecimal.TEN)
                .user(UserBuilder.of()
                        .id("user-id")
                        .build())
                .build();
        WalletResponseDTO walletResponseDTO = WalletMapper.entityToDTO(wallet);
        assertThat(walletResponseDTO.getId()).isEqualTo(wallet.getId());
        assertThat(walletResponseDTO.getBalance()).isEqualTo(BigDecimal.TEN);
    }
}
