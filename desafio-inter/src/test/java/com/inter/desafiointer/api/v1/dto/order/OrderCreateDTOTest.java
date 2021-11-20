package com.inter.desafiointer.api.v1.dto.order;

import org.junit.jupiter.api.Test;

import static com.inter.desafiointer.domain.OrderType.BUY;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderCreateDTOTest {

    @Test
    public void whenBuildDTOThenReturnDTO () {
        OrderCreateDTO orderCreateDTO = OrderCreateDTO.Builder.of()
                .amount(5)
                .code("BIDI11")
                .walletId("some-wallet-id")
                .type(BUY.toString())
                .build();
        assertThat(orderCreateDTO.getAmount()).isEqualTo(5);
        assertThat(orderCreateDTO.getCode()).isEqualTo("BIDI11");
        assertThat(orderCreateDTO.getWalletId()).isEqualTo("some-wallet-id");
        assertThat(orderCreateDTO.getType()).isEqualTo(BUY.toString());
    }
}
