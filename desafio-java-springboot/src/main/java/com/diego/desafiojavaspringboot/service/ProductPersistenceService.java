package com.diego.desafiojavaspringboot.service;

import com.diego.desafiojavaspringboot.exception.ProductBadRequestException;
import com.diego.desafiojavaspringboot.model.Product;
import com.diego.desafiojavaspringboot.repository.ProductRepository;
import com.diego.desafiojavaspringboot.validation.ProductValidation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductPersistenceService {

    private ProductRepository productRepository;
    private ProductSearchService productSearchService;

    public ProductPersistenceService(ProductRepository productRepository, ProductSearchService productSearchService) {
        this.productRepository = productRepository;
        this.productSearchService = productSearchService;
    }

    public Product save (Product product) {
        if (product.getId() != null) {
            throw new ProductBadRequestException("O identificador do produto deve estar nulo");
        }
        ProductValidation.validate(product);
        product.setId(UUID.randomUUID().toString());
        return productRepository.save(product);
    }

    public Product update (String id, Product product) {
        if (id == null) {
            throw new ProductBadRequestException("O identificador do produto não pode estar nulo");
        }
        ProductValidation.validate(product);
        productSearchService.findById(id);
        product.setId(id);
        return productRepository.save(product);
    }

    public void delete (String id) {
        Product product = productSearchService.findById(id);
        productRepository.delete(product);
    }
}
