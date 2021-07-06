package com.example.springbootmongodb.api.v1.dto.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "EmployeeUpdate")
public class EmployeeUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Employee name")
    private String name;
    @ApiModelProperty(value = "Employee address")
    private String address;

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

    @Override
    public String toString() {
        return "EmployeeUpdateDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
            '}';
    }

    public static class Builder {

        private EmployeeUpdateDTO employee;

        private Builder() {
            employee = new EmployeeUpdateDTO();
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

        public EmployeeUpdateDTO build () {
            return employee;
        }
    }
}
