package com.inter.desafiointer.service;

import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.domain.CompanyStatus;
import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.exception.BadRequestException;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class CompanyPersistenceService {

    private final CompanyRepository companyRepository;

    public CompanyPersistenceService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company save(Company company) {
        validateFields(company);
        company.setId(UUID.randomUUID().toString());
        return companyRepository.save(company);
    }

    public Company update(String id, Company company) {
        findById(id);
        validateFields(company);
        company.setId(id);
        return companyRepository.save(company);
    }

    public void delete(String id) {
        Company company = findById(id);
        companyRepository.delete(company);
    }

    public Company patch (Map<String, String> patch, String id) {
        Company company = findById(id);
        patch.forEach((key, value) -> {
            switch (key) {
                case "status":
                    company.setStatus(CompanyStatus.of(value));
                    break;
                case "price":
                    try {
                        company.setPrice(new BigDecimal(value));
                    } catch (Exception e){
                        throw new BadRequestException("Invalid price");
                    }
                    break;
            }
        });
        return companyRepository.save(company);
    }

    protected Company findById (String id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found"));
    }

    private void validateFields (Company company) {
        if (company.getName() == null || company.getName().isEmpty()) {
            throw new BadRequestException("Name is mandatory");
        } else if (company.getCode() == null || company.getCode().isEmpty()) {
            throw new BadRequestException("Code is mandatory");
        } else if (company.getPrice() == null) {
            throw new BadRequestException("Price is mandatory");
        }
    }
}