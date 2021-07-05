package com.example.springbootmongodb.api.v1.dto.employee;

import java.io.Serializable;

public class EmployeeUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
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
