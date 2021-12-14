package com.diego.homebroker.repository;

import com.diego.homebroker.domain.Company;
import com.diego.homebroker.domain.Order;
import com.diego.homebroker.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("select o from Order o where (:code is null or o.company.code = :code)")
    List<Order> find(@Param("code") String code);

    List<Order> findByWallet(Wallet wallet);

    long countByCompany(Company company);
}
