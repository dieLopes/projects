package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.CompanyBuilder;
import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.exception.BadRequestException;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static com.inter.desafiointer.domain.CompanyStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyPersistenceServiceTest {

    @InjectMocks
    private CompanyPersistenceService companyPersistenceService;
    @Mock
    private CompanyRepository companyRepository;

    @Test
    public void whenCreateCompanyThenSaveCompany () {
        Company company = buildCompany(null, "Some Name", "SOME4");
        company.setId(null);
        when(companyRepository.save(eq(company))).thenReturn(company);
        companyPersistenceService.save(company);
        verify(companyRepository).save(any(Company.class));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenCreateCompanyWithoutNameThenReturnBadRequest () {
        Company company = buildCompany(null, null, "SOME4");
        assertThatThrownBy(() ->  companyPersistenceService.save(company))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Name is mandatory");
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenCreateCompanyWithoutCodeThenReturnBadRequest () {
        Company company = buildCompany(null, "Some Name", null);
        assertThatThrownBy(() ->  companyPersistenceService.save(company))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Code is mandatory");
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenCreateCompanyWithoutPriceThenReturnBadRequest () {
        Company company = buildCompany(null, "Some Name", "SOME4");
        company.setPrice(null);
        assertThatThrownBy(() ->  companyPersistenceService.save(company))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Price is mandatory");
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenUpdateCompanyThenSaveCompany () {
        Company company = buildCompany("some-id", "Some Name", "SOME4");
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.of(company));
        when(companyRepository.save(eq(company))).thenReturn(company);
        companyPersistenceService.update(company.getId(), company);
        verify(companyRepository).findById(eq("some-id"));
        verify(companyRepository).save(any(Company.class));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenUpdateCompanyInvalidIdThenReturnNotFound () {
        Company company = buildCompany("company-id", "Some Name", "SOME4");
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.empty());
        assertThatThrownBy(() ->  companyPersistenceService.update("some-id", company))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Company not found");
        verify(companyRepository).findById(eq("some-id"));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenUpdateCompanyWithoutNameThenReturnBadRequest () {
        Company company = buildCompany("some-id", null, "SOME4");
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.of(company));
        assertThatThrownBy(() ->  companyPersistenceService.update("some-id", company))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Name is mandatory");
        verify(companyRepository).findById(eq("some-id"));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenUpdateCompanyWithoutCodeThenReturnBadRequest () {
        Company company = buildCompany("some-id", "Some Name", null);
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.of(company));
        assertThatThrownBy(() ->  companyPersistenceService.update("some-id", company))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Code is mandatory");
        verify(companyRepository).findById(eq("some-id"));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenUpdateCompanyWithoutPriceThenReturnBadRequest () {
        Company company = buildCompany("some-id", "Some Name", "SOME4");
        company.setPrice(null);
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.of(company));
        assertThatThrownBy(() ->  companyPersistenceService.update("some-id", company))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Price is mandatory");
        verify(companyRepository).findById(eq("some-id"));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenDeleteCompanyThenDeleteCompany () {
        Company company = buildCompany("some-id", "Some Name", "SOME4");
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.of(company));
        companyPersistenceService.delete(company.getId());
        verify(companyRepository).findById(eq("some-id"));
        verify(companyRepository).delete(any(Company.class));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenDeleteCompanyInvalidIdThenReturnNotFound () {
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.empty());
        assertThatThrownBy(() ->  companyPersistenceService.delete("some-id"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Company not found");
        verify(companyRepository).findById(eq("some-id"));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenPatchCompanyThenUpdateCompanyAttributes () {
        Company company = buildCompany("some-id", "Some Name", "SOME4");
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.of(company));
        companyPersistenceService.patch(Map.of("status", "INACTIVE", "price", "50"), "some-id");
        verify(companyRepository).findById(eq("some-id"));
        verify(companyRepository).save(any(Company.class));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenPatchCompanyWithInvalidAttributesThenIgnoreAttributeAndUpdateCompany () {
        Company company = buildCompany("some-id", "Some Name", "SOME4");
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.of(company));
        companyPersistenceService.patch(Map.of("invalidAttribute", "INACTIVE"), "some-id");
        verify(companyRepository).findById(eq("some-id"));
        verify(companyRepository).save(any(Company.class));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenPatchCompanyInvalidIdThenReturnNotFound () {
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.empty());
        assertThatThrownBy(() ->  companyPersistenceService.patch(Map.of(), "some-id"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Company not found");
        verify(companyRepository).findById(eq("some-id"));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenPatchCompanyInvalidStatusThenReturnBadRequest () {
        Company company = buildCompany("some-id", "Some Name", "SOME4");
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.of(company));
        assertThatThrownBy(() -> companyPersistenceService.patch(Map.of("status", "INVALID", "price", "50"), "some-id"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Invalid status");
        verify(companyRepository).findById(eq("some-id"));
        verifyNoMoreInteractions(companyRepository);
    }

    @Test
    public void whenPatchCompanyInvalidPriceThenReturnBadRequest () {
        Company company = buildCompany("some-id", "Some Name", "SOME4");
        when(companyRepository.findById(eq("some-id"))).thenReturn(Optional.of(company));
        assertThatThrownBy(() -> companyPersistenceService.patch(Map.of("status", "INACTIVE", "price", "INVALID"), "some-id"))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Invalid price");
        verify(companyRepository).findById(eq("some-id"));
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
