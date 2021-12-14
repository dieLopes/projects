package com.diego.homebroker.service;

import com.diego.homebroker.domain.CompanyStatus;
import com.diego.homebroker.exception.BadRequestException;
import com.diego.homebroker.exception.NotFoundException;
import com.diego.homebroker.repository.CompanyRepository;
import com.diego.homebroker.repository.OrderRepository;
import com.diego.homebroker.domain.Company;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class CompanyPersistenceService {

    private final CompanyRepository companyRepository;
    private final OrderRepository orderRepository;

    public CompanyPersistenceService(CompanyRepository companyRepository,
                                     OrderRepository orderRepository) {
        this.companyRepository = companyRepository;
        this.orderRepository = orderRepository;
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
        validate(company);
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

    private void validate (Company company) {
        if (orderRepository.countByCompany(company) > 0) {
            throw new BadRequestException("There are orders to company");
        }
    }
}