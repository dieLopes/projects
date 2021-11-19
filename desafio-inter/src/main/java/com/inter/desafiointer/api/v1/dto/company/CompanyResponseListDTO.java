package com.inter.desafiointer.api.v1.dto.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "CompanyResponseList")
public class CompanyResponseListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of companies")
    private List<CompanyResponseDTO> companies;

    public CompanyResponseListDTO(List<CompanyResponseDTO> companies) {
        this.companies = companies;
    }

    public List<CompanyResponseDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyResponseDTO> companies) {
        this.companies = companies;
    }
}