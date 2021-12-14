package com.diego.homebroker.builder;

import com.diego.homebroker.domain.CompanyStatus;
import com.diego.homebroker.domain.Company;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyBuilderTest {

    @Test
    public void whenBuildClassThenReturnClass () {
        Company company = CompanyBuilder.of()
                .id("some-id")
                .name("Some Name")
                .code("SOME4")
                .price(BigDecimal.TEN)
                .status(CompanyStatus.INACTIVE)
                .build();
        assertThat(company.getId()).isEqualTo("some-id");
        assertThat(company.getName()).isEqualTo("Some Name");
        assertThat(company.getCode()).isEqualTo("SOME4");
        assertThat(company.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(company.getStatus()).isEqualTo(CompanyStatus.INACTIVE);
    }
}
