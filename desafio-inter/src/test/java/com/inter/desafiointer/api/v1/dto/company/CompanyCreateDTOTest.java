package com.inter.desafiointer.api.v1.dto.company;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyCreateDTOTest {

    @Test
    public void whenBuildDTOThenReturnDTO () {
        CompanyCreateDTO companyCreateDTO = CompanyCreateDTO.Builder.of()
                .name("Some Name")
                .price(BigDecimal.TEN)
                .code("BIDI11")
                .build();
        assertThat(companyCreateDTO.getName()).isEqualTo("Some Name");
        assertThat(companyCreateDTO.getCode()).isEqualTo("BIDI11");
        assertThat(companyCreateDTO.getPrice()).isEqualTo(BigDecimal.TEN);
    }
}
