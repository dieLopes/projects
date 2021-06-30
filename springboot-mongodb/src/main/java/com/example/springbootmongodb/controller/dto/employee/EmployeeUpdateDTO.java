package com.example.springbootmongodb.controller.dto.employee;

import java.io.Serializable;

public class EmployeeUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String bossId;

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

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }

    @Override
    public String toString() {
        return "EmployeeUpdateDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bossId='" + bossId + '\'' +
            '}';
    }
}
