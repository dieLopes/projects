package com.diego.taskboard.api.v1.dto.employee;

import com.diego.taskboard.api.v1.dto.tenant.TenantResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "EmployeeResponse")
public class EmployeeResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("Employee id")
    private String id;
    @ApiModelProperty("Employee name")
    private String name;
    @ApiModelProperty("Employee address")
    private String address;
    @ApiModelProperty("Employee tenant")
    private TenantResponseDTO tenant;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TenantResponseDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantResponseDTO tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "EmployeeResponseDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
            '}';
    }
}
