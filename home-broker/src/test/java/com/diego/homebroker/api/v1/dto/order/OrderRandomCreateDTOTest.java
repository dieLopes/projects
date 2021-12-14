package com.diego.homebroker.api.v1.dto.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRandomCreateDTOTest {

    @Test
    public void whenBuildDTOThenReturnDTO () {
        OrderRandomCreateDTO orderCreateDTO = OrderRandomCreateDTO.Builder.of()
                .total(new BigDecimal(100))
                .cpf("11111111111")
                .amount(5)
                .build();
        assertThat(orderCreateDTO.getTotal()).isEqualTo(new BigDecimal(100));
        assertThat(orderCreateDTO.getCpf()).isEqualTo("11111111111");
        assertThat(orderCreateDTO.getAmount()).isEqualTo(5);
    }
}
