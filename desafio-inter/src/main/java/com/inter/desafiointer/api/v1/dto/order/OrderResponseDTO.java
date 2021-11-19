package com.inter.desafiointer.api.v1.dto.order;

import com.inter.desafiointer.api.v1.dto.company.CompanyResponseDTO;
import com.inter.desafiointer.api.v1.dto.wallet.WalletResponseDTO;
import com.inter.desafiointer.domain.OrderType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(value = "OrderResponse")
public class OrderResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Order id", required = true)
    private String id;
    @ApiModelProperty(value = "Order date", required = true)
    private LocalDateTime date;
    @ApiModelProperty(value = "Order type", required = true, allowableValues = "BUY,SELL")
    private OrderType type;
    @ApiModelProperty(value = "Amount company shares", required = true)
    private int amount;
    @ApiModelProperty(value = "Unit price of the company's share", required = true)
    private BigDecimal unitPrice;
    @ApiModelProperty(value = "Order total price", required = true)
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "Company", required = true)
    private CompanyResponseDTO company;
    @ApiModelProperty(value = "Wallet", required = true)
    private WalletResponseDTO wallet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CompanyResponseDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyResponseDTO company) {
        this.company = company;
    }

    public WalletResponseDTO getWallet() {
        return wallet;
    }

    public void setWallet(WalletResponseDTO wallet) {
        this.wallet = wallet;
    }
}
