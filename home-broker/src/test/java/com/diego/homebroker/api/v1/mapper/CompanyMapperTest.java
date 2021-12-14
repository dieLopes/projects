package com.diego.homebroker.api.v1.mapper;

import com.diego.homebroker.api.v1.dto.company.CompanyCreateDTO;
import com.diego.homebroker.api.v1.dto.company.CompanyResponseDTO;
import com.diego.homebroker.api.v1.dto.company.CompanyUpdateDTO;
import com.diego.homebroker.builder.CompanyBuilder;
import com.diego.homebroker.domain.Company;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.diego.homebroker.domain.CompanyStatus.ACTIVE;
import static com.diego.homebroker.domain.CompanyStatus.INACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class CompanyMapperTest {

    @Test
    public void whenConvertCreateDTOToEntityThenReturn () {
        CompanyCreateDTO companyCreateDTO = CompanyCreateDTO.Builder.of()
                .name("Some Name")
                .code("SOME4")
                .price(BigDecimal.TEN)
                .build();
        Company company = CompanyMapper.createDtoToEntity(companyCreateDTO);
        assertThat(company.getId()).isNullOrEmpty();
        assertThat(company.getName()).isEqualTo("Some Name");
        assertThat(company.getCode()).isEqualTo("SOME4");
        assertThat(company.getPrice()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void whenConvertUpdateDTOToEntityThenReturn () {
        CompanyUpdateDTO companyUpdateDTO = CompanyUpdateDTO.Builder.of()
                .name("Some Name")
                .code("SOME4")
                .price(BigDecimal.TEN)
                .status(INACTIVE.toString())
                .build();
        Company company = CompanyMapper.updateDtoToEntity(companyUpdateDTO);
        assertThat(company.getId()).isNullOrEmpty();
        assertThat(company.getName()).isEqualTo("Some Name");
        assertThat(company.getCode()).isEqualTo("SOME4");
        assertThat(company.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(company.getStatus()).isEqualTo(INACTIVE);
    }

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        Company company = CompanyBuilder.of()
                .id("some-id")
                .name("Some Name")
                .code("SOME4")
                .price(BigDecimal.TEN)
                .status(INACTIVE)
                .build();
        CompanyResponseDTO companyResponseDTO = CompanyMapper.entityToDTO(company);
        assertThat(companyResponseDTO.getId()).isEqualTo("some-id");
        assertThat(companyResponseDTO.getName()).isEqualTo("Some Name");
        assertThat(companyResponseDTO.getCode()).isEqualTo("SOME4");
        assertThat(companyResponseDTO.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(companyResponseDTO.getStatus()).isEqualTo(INACTIVE.toString());
    }

    @Test
    public void whenConvertEntitiesToListDTOThenReturn () {
        List<Company> companies = List.of(
                CompanyBuilder.of()
                        .id("some-id")
                        .name("Some Name")
                        .code("SOME1")
                        .price(BigDecimal.TEN)
                        .status(INACTIVE)
                        .build(),
                CompanyBuilder.of()
                        .id("another-id")
                        .name("Another Name")
                        .code("SOME2")
                        .price(new BigDecimal(20))
                        .status(ACTIVE)
                        .build());
        List<CompanyResponseDTO> companyResponseDTOS = CompanyMapper.entitiesToDTOs(companies);
        assertThat(companyResponseDTOS).hasSize(2)
                .extracting(CompanyResponseDTO::getId,
                        CompanyResponseDTO::getName,
                        CompanyResponseDTO::getCode,
                        CompanyResponseDTO::getPrice,
                        CompanyResponseDTO::getStatus)
                .containsExactlyInAnyOrder(
                        tuple("some-id", "Some Name", "SOME1", BigDecimal.TEN, INACTIVE.toString()),
                        tuple("another-id", "Another Name", "SOME2", new BigDecimal(20), ACTIVE.toString())
                );
    }
}