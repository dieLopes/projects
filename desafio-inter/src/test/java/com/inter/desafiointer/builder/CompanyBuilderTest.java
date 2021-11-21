package com.inter.desafiointer.builder;

import com.inter.desafiointer.domain.Company;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.inter.desafiointer.domain.CompanyStatus.INACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyBuilderTest {

    @Test
    public void whenBuildClassThenReturnClass () {
        Company company = CompanyBuilder.of()
                .id("some-id")
                .name("Some Name")
                .code("SOME4")
                .price(BigDecimal.TEN)
                .status(INACTIVE)
                .build();
        assertThat(company.getId()).isEqualTo("some-id");
        assertThat(company.getName()).isEqualTo("Some Name");
        assertThat(company.getCode()).isEqualTo("SOME4");
        assertThat(company.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(company.getStatus()).isEqualTo(INACTIVE);
    }
}
