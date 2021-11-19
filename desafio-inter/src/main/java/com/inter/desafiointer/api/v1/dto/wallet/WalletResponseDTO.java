package com.inter.desafiointer.api.v1.dto.wallet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel(value = "WalletResponse")
public class WalletResponseDTO {

    @ApiModelProperty("Wallet id")
    private String id;
    @ApiModelProperty("Wallet id")
    private BigDecimal balance = BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
