package com.diego.homebroker.service;

import com.diego.homebroker.domain.CompanyStatus;
import com.diego.homebroker.exception.NotFoundException;
import com.diego.homebroker.repository.CompanyRepository;
import com.diego.homebroker.domain.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanySearchService {

    private final CompanyRepository companyRepository;

    public CompanySearchService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> find(String status) {
        if (status == null) {
            return companyRepository.find(null);
        }
        return companyRepository.find(CompanyStatus.of(status));
    }

    public Company findByCode(String code) {
        return companyRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Company not found"));
    }
}
