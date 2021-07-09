package com.diego.desafiojavaspringboot.repository;

import com.diego.desafiojavaspringboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("select p from Product p " +
            "where (:search is null or p.name like %:search% or p.description like %:search%) " +
            "and (:minPrice is null or p.price >= :minPrice) " +
            "and (:maxPrice is null or p.price <= :maxPrice) ")
    List<Product> findByFilter(@Param("search") String search,
                               @Param("maxPrice") BigDecimal maxPrice,
                               @Param("minPrice") BigDecimal minPrice);
}
