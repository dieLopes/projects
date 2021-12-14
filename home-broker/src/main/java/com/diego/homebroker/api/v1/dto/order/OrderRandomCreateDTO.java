package com.diego.homebroker.api.v1.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "OrderRandomCreate")
public class OrderRandomCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("User cpf")
    private String cpf;
    @ApiModelProperty("Total for orders")
    private BigDecimal total;
    @ApiModelProperty("Amount for companies")
    private int amount;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static class Builder {

        private final OrderRandomCreateDTO order;

        private Builder() {
            order = new OrderRandomCreateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder cpf (String cpf) {
            order.cpf = cpf;
            return this;
        }

        public Builder total (BigDecimal total) {
            order.total = total;
            return this;
        }

        public Builder amount (int amount) {
            order.amount = amount;
            return this;
        }

        public OrderRandomCreateDTO build () {
            return order;
        }
    }
}