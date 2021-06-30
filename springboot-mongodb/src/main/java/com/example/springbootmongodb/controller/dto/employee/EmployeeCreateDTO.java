package com.example.springbootmongodb.controller.dto.employee;

import java.io.Serializable;

public class EmployeeCreateDTO implements Serializable {

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
        return "EmployeeCreateDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
            '}';
    }
}
