package com.inter.desafiointer.service;

import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.domain.CompanyStatus;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
