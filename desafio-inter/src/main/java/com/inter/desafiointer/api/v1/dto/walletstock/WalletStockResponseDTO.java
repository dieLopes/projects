package com.inter.desafiointer.api.v1.dto.walletstock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "WalletStockResponse")
public class WalletStockResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("Wallet stock id")
    private String id;
    @ApiModelProperty("Company code")
    private String companyCode;
    @ApiModelProperty("Amount of company stocks")
    private int amount;
    @ApiModelProperty("Balance of company stocks")
    private BigDecimal balance;
    @ApiModelProperty("Average price")
    private BigDecimal averagePrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }
}
