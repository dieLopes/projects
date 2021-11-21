package com.inter.desafiointer.api.v1.dto.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRandomCreateDTOTest {

    @Test
    public void whenBuildDTOThenReturnDTO () {
        OrderRandomCreateDTO orderCreateDTO = OrderRandomCreateDTO.Builder.of()
                .total(new BigDecimal(100))
                .walletId("some-wallet-id")
                .build();
        assertThat(orderCreateDTO.getTotal()).isEqualTo(new BigDecimal(100));
        assertThat(orderCreateDTO.getWalletId()).isEqualTo("some-wallet-id");
    }
}
