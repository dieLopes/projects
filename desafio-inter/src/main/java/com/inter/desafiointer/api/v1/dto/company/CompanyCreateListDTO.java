package com.inter.desafiointer.api.v1.dto.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "CompanyCreateList")
public class CompanyCreateListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of companies")
    private List<CompanyCreateDTO> companies;

    public CompanyCreateListDTO() { }

    public CompanyCreateListDTO(List<CompanyCreateDTO> companies) {
        this.companies = companies;
    }

    public List<CompanyCreateDTO> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyCreateDTO> companies) {
        this.companies = companies;
    }
}
