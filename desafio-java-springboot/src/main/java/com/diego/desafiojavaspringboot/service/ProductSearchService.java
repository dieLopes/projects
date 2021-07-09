package com.diego.desafiojavaspringboot.service;

import com.diego.desafiojavaspringboot.exception.ProductNotFoundException;
import com.diego.desafiojavaspringboot.model.Product;
import com.diego.desafiojavaspringboot.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductSearchService {

    private ProductRepository productRepository;

    public ProductSearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Product findById (String id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Produto não encontrado"));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Product> findAll () {
        return productRepository.findAll();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Product> findByFilter (String search, BigDecimal maxPrice, BigDecimal minPrice) {
        return productRepository.findByFilter(search, maxPrice, minPrice);
    }
}
