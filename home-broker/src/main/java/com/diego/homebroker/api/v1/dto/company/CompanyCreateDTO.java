package com.diego.homebroker.api.v1.dto.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "CompanyCreate")
public class CompanyCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Company name", required = true)
    private String name;
    @ApiModelProperty(value = "Company code", required = true)
    private String code;
    @ApiModelProperty(value = "Company price", required = true)
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static class Builder {

        private final CompanyCreateDTO company;

        private Builder() {
            company = new CompanyCreateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder name (String name) {
            company.name = name;
            return this;
        }

        public Builder code (String code) {
            company.code = code;
            return this;
        }

        public Builder price (BigDecimal price) {
            company.price = price;
            return this;
        }

        public CompanyCreateDTO build () {
            return company;
        }
    }
}
