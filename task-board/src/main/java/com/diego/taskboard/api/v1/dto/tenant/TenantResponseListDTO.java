package com.diego.taskboard.api.v1.dto.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "TenantResponseList")
public class TenantResponseListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of tenants")
    private List<TenantResponseDTO> tenants = new ArrayList<>();

    public TenantResponseListDTO() { }

    public TenantResponseListDTO(List<TenantResponseDTO> tenants) {
        this.tenants = tenants;
    }

    public List<TenantResponseDTO> getTenants() {
        return tenants;
    }

    public void setTenants(List<TenantResponseDTO> tenants) {
        this.tenants = tenants;
    }
}