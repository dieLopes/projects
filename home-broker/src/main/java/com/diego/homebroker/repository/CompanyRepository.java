package com.diego.homebroker.repository;

import com.diego.homebroker.domain.CompanyStatus;
import com.diego.homebroker.domain.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query("select c from Company c where (:status is null or c.status = :status)")
    List<Company> find(CompanyStatus status);

    Optional<Company> findByCode(String code);

    Optional<Company> findByCodeAndStatus(String code, CompanyStatus status);

    List<Company> findByStatusOrderByPriceAsc(CompanyStatus status, Pageable pageable);
}