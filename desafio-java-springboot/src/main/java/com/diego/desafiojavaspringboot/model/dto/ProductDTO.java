package com.diego.desafiojavaspringboot.model.dto;

import java.math.BigDecimal;

public class ProductDTO {

    private String name;
    private String description;
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static class Builder {

        private ProductDTO product;

        private Builder () {
            product = new ProductDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder name(String name) {
            product.setName(name);
            return this;
        }

        public Builder description(String description) {
            product.setDescription(description);
            return this;
        }

        public Builder price(BigDecimal price) {
            product.setPrice(price);
            return this;
        }

        public ProductDTO build() {
            return product;
        }
    }
}
