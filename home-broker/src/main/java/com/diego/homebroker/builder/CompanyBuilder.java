package com.diego.homebroker.builder;

import com.diego.homebroker.domain.CompanyStatus;
import com.diego.homebroker.domain.Company;

import java.math.BigDecimal;

public class CompanyBuilder {

    private final Company company;

    private CompanyBuilder() {
        company = new Company();
    }

    public static CompanyBuilder of () {
        return new CompanyBuilder();
    }

    public CompanyBuilder id (String id) {
        company.setId(id);
        return this;
    }

    public CompanyBuilder name (String name) {
        company.setName(name);
        return this;
    }

    public CompanyBuilder code (String code) {
        company.setCode(code);
        return this;
    }

    public CompanyBuilder price (BigDecimal price) {
        company.setPrice(price);
        return this;
    }

    public CompanyBuilder status (CompanyStatus status) {
        company.setStatus(status);
        return this;
    }

    public Company build () {
        return company;
    }
}
