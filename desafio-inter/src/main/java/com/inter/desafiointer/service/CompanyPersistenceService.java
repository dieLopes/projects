package com.inter.desafiointer.service;

import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.domain.CompanyStatus;
import com.inter.desafiointer.exception.BadRequestException;
import com.inter.desafiointer.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CompanyPersistenceService {

    private final CompanyRepository companyRepository;
    private final CompanySearchService companySearchService;

    public CompanyPersistenceService(CompanyRepository companyRepository,
                                     CompanySearchService companySearchService) {
        this.companyRepository = companyRepository;
        this.companySearchService = companySearchService;
    }

    public Company save (Company company) {
        company.setId(UUID.randomUUID().toString());
        return companyRepository.save(company);
    }

    public List<Company> save(List<Company> companies) {
        companies.forEach(this::save);
        return companies;
    }

    public Company patch (Map<String, String> patch, String id) {
        Company company = companySearchService.findById(id);
        try {
            patch.forEach((key, value) -> {
                switch (key) {
                    case "status": company.setStatus(CompanyStatus.valueOf(value)); break;
                    case "price": company.setPrice(new BigDecimal(value)); break;
                }
            });
        } catch (Exception e) {
            throw new BadRequestException("Invalid property");
        }
        return companyRepository.save(company);
    }

    public Company update(String id, Company company) {
        companySearchService.findById(id);
        company.setId(id);
        return companyRepository.save(company);
    }

    public void delete(String id) {
        Company company = companySearchService.findById(id);
        companyRepository.delete(company);
    }
}
