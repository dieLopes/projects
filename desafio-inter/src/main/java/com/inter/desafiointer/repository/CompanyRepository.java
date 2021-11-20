package com.inter.desafiointer.repository;

import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.domain.CompanyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query("select c from Company c where (:status is null or c.status = :status)")
    List<Company> find(CompanyStatus status);

    Optional<Company> findByCode(String code);
}