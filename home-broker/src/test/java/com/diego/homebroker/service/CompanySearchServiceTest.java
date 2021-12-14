package com.diego.homebroker.service;

import com.diego.homebroker.builder.CompanyBuilder;
import com.diego.homebroker.domain.Company;
import com.diego.homebroker.exception.BadRequestException;
import com.diego.homebroker.exception.NotFoundException;
import com.diego.homebroker.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.diego.homebroker.domain.CompanyStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanySearchServiceTest {

    @InjectMocks
    private CompanySearchService companySearchService;
    @Mock
    private CompanyRepository companyRepository;

    @Test
    public void whenFindCompaniesThenReturnCompanies () {
        List<Company> companies = List.of(
                buildCompany("some-id", "Some Name", "SOME3"),
                buildCompany("another-id", "Another Name", "SOME4"));
        when(companyRepository.find(ACTIVE)).thenReturn(companies);
        assertThat(companySearchService.find("ACTIVE")).hasSize(2)
                .extracting(Company::getId,
                        Company::getName,
                        Company::getCode)
                .containsExactlyInAnyOrder(
                        tuple("some-id", "Some Name", "SOME3"),
                        tuple("another-id", "Another Name", "SOME4")
                );
        verify(companyRepository).find(eq(ACTIVE));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenFindCompaniesWithoutStatusThenReturnCompanies () {
        List<Company> companies = List.of(
                buildCompany("some-id", "Some Name", "SOME3"),
                buildCompany("another-id", "Another Name", "SOME4"));
        when(companyRepository.find(null)).thenReturn(companies);
        assertThat(companySearchService.find(null)).hasSize(2)
                .extracting(Company::getId,
                        Company::getName,
                        Company::getCode)
                .containsExactlyInAnyOrder(
                        tuple("some-id", "Some Name", "SOME3"),
                        tuple("another-id", "Another Name", "SOME4")
                );
        verify(companyRepository).find(eq(null));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenFindCompaniesWithInvalidStatusThenReturnBadRequest () {
        assertThatThrownBy(() -> companySearchService.find("INVALID"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Invalid status");
        verifyZeroInteractions(companyRepository);
    }

    @Test
    public void whenFindCompanyByCodeThenReturnCompany () {
        Company company = buildCompany("some-id", "Some Name", "SOME4");
        when(companyRepository.findByCode(eq("SOME4"))).thenReturn(Optional.of(company));
        assertThat(companySearchService.findByCode("SOME4")).satisfies(findComp -> {
            assertThat(findComp.getId()).isEqualTo(company.getId());
            assertThat(findComp.getName()).isEqualTo(company.getName());
            assertThat(findComp.getCode()).isEqualTo(company.getCode());
        });
        verify(companyRepository).findByCode(eq("SOME4"));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenFindCompanyByInvalidCodeThenReturnNotFound () {
        when(companyRepository.findByCode(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> companySearchService.findByCode("INVALID3"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Company not found");
        verify(companyRepository).findByCode(eq("INVALID3"));
        verifyNoMoreInteractions(companyRepository);
    }

    private Company buildCompany(String id, String name, String code) {
        return CompanyBuilder.of()
                .id(id)
                .name(name)
                .code(code)
                .price(BigDecimal.TEN)
                .status(ACTIVE)
                .build();
    }
}
