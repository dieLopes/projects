package com.example.springbootmongodb.api.v1.dto.tenant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TenantResponseListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
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