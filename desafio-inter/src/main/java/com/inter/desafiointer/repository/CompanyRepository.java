package com.inter.desafiointer.repository;

import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.domain.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, String> {

    List<Company> findByStatus(CompanyStatus status);
}
