package com.inter.desafiointer.api.v1.dto.order;

import com.inter.desafiointer.domain.OrderType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "OrderCreate")
public class OrderCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Wallet id", required = true)
    private String walletId;
    @ApiModelProperty(value = "Order type", required = true, allowableValues = "BUY,SELL")
    private OrderType type;
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

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
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
}