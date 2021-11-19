package com.inter.desafiointer.api.v1.dto.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "CompanyUpdate")
public class CompanyUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Company name", required = true)
    private String name;
    @ApiModelProperty(value = "Company code", required = true)
    private String code;
    @ApiModelProperty(value = "Company price", required = true)
    private String price;
    @ApiModelProperty(value = "Company status", required = true)
    private String status;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
