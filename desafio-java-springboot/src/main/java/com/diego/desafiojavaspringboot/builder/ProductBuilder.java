package com.diego.desafiojavaspringboot.builder;

import com.diego.desafiojavaspringboot.model.Product;

import java.math.BigDecimal;

public class ProductBuilder {

    private Product product;

    private ProductBuilder () {
        product = new Product();
    }

    public static ProductBuilder of () {
        return new ProductBuilder();
    }

    public ProductBuilder id(String id) {
        product.setId(id);
        return this;
    }

    public ProductBuilder name(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder description(String description) {
        product.setDescription(description);
        return this;
    }

    public ProductBuilder price(BigDecimal price) {
        product.setPrice(price);
        return this;
    }

    public Product build() {
        return product;
    }
}