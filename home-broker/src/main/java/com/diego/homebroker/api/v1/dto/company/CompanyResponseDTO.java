package com.diego.homebroker.api.v1.dto.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "CompanyResponse")
public class CompanyResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("Company id")
    private String id;
    @ApiModelProperty("Company name")
    private String name;
    @ApiModelProperty("Company code")
    private String code;
    @ApiModelProperty("Company price")
    private BigDecimal price;
    @ApiModelProperty("Company status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
