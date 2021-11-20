package com.inter.desafiointer.api.v1.dto.company;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.inter.desafiointer.domain.CompanyStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyUpdateDTOTest {

    @Test
    public void whenBuildDTOThenReturnDTO () {
        CompanyUpdateDTO companyCreateDTO = CompanyUpdateDTO.Builder.of()
                .name("Some Name")
                .price(BigDecimal.TEN)
                .code("BIDI11")
                .status(ACTIVE.toString())
                .build();
        assertThat(companyCreateDTO.getName()).isEqualTo("Some Name");
        assertThat(companyCreateDTO.getCode()).isEqualTo("BIDI11");
        assertThat(companyCreateDTO.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(companyCreateDTO.getStatus()).isEqualTo(ACTIVE.toString());
    }
}
