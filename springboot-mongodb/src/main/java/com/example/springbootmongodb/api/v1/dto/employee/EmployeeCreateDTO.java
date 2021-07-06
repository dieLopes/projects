package com.example.springbootmongodb.api.v1.dto.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "EmployeeCreate")
public class EmployeeCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Employee name", required = true)
    private String name;
    @ApiModelProperty(value = "Employee address", required = true)
    private String address;
    @ApiModelProperty(value = "Employee tenant id", required = true)
    private String tenantId;

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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "EmployeeCreateDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tenantId='" + tenantId + '\'' +
            '}';
    }

    public static class Builder {

        private EmployeeCreateDTO employee;

        private Builder() {
            employee = new EmployeeCreateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder name (String name) {
            employee.setName(name);
            return this;
        }

        public Builder address (String address) {
            employee.setAddress(address);
            return this;
        }

        public Builder tenantId (String tenantId) {
            employee.setTenantId(tenantId);
            return this;
        }

        public EmployeeCreateDTO build () {
            return employee;
        }
    }
}