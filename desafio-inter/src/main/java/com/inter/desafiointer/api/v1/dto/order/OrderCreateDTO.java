package com.inter.desafiointer.api.v1.dto.order;

import com.inter.desafiointer.api.v1.dto.company.CompanyCreateDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "OrderCreate")
public class OrderCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Wallet id", required = true)
    private String walletId;
    @ApiModelProperty(value = "Order type", required = true, allowableValues = "BUY,SELL")
    private String type;
    @ApiModelProperty(value = "Company code", required = true)
    private String code;
    @ApiModelProperty(value = "Amount company shares", required = true)
    private int amount;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static class Builder {

        private final OrderCreateDTO order;

        private Builder() {
            order = new OrderCreateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder walletId (String walletId) {
            order.walletId = walletId;
            return this;
        }

        public Builder type (String type) {
            order.type = type;
            return this;
        }

        public Builder code (String code) {
            order.code = code;
            return this;
        }

        public Builder amount (int amount) {
            order.amount = amount;
            return this;
        }

        public OrderCreateDTO build () {
            return order;
        }
    }
}