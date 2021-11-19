package com.inter.desafiointer.service;

import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.domain.CompanyStatus;
import com.inter.desafiointer.exception.BadRequestException;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanySearchService {

    private final CompanyRepository companyRepository;

    public CompanySearchService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll () {
        return companyRepository.findAll();
    }

    public Company findById (String id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found"));
    }

    public List<Company> findByStatus(String status) {
        try {
            return companyRepository.findByStatus(CompanyStatus.valueOf(status));
        } catch (Exception e) {
            throw new BadRequestException("Invalid Status");
        }
    }
}
