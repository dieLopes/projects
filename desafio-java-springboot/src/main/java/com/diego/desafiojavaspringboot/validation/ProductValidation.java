package com.diego.desafiojavaspringboot.validation;

import com.diego.desafiojavaspringboot.exception.ProductBadRequestException;
import com.diego.desafiojavaspringboot.model.Product;

import java.math.BigDecimal;

public final class ProductValidation {

    public static void validate (Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new ProductBadRequestException("Nome é um campo obrigatório");
        }
        if (product.getDescription() == null || product.getDescription().isBlank()) {
            throw new ProductBadRequestException("Descrição é um campo obrigatório");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductBadRequestException("Preço é um campo obrigatório e deve ser maior do que zero");
        }
    }
}
