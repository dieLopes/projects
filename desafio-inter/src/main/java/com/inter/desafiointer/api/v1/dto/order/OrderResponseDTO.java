package com.inter.desafiointer.api.v1.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(value = "OrderResponse")
public class OrderResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("Order id")
    private String id;
    @ApiModelProperty("Order date")
    private LocalDateTime date;
    @ApiModelProperty("Order type")
    private String type;
    @ApiModelProperty("Amount company shares")
    private int amount;
    @ApiModelProperty("Unit price of the company's share")
    private BigDecimal unitPrice;
    @ApiModelProperty("Order total price")
    private BigDecimal totalPrice;
    @ApiModelProperty("Company code")
    private String companyCode;
    @ApiModelProperty("User cpf")
    private String cpf;
    @ApiModelProperty("Order status")
    private String status;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
