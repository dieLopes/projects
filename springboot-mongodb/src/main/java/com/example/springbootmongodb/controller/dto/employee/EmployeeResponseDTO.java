package com.example.springbootmongodb.controller.dto.employee;

import java.io.Serializable;

public class EmployeeResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String address;
    private String bossId;
    private EmployeeResponseDTO boss;

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

    public String getBossId() {
        return bossId;
    }

    public void setBossId(String bossId) {
        this.bossId = bossId;
    }

    public EmployeeResponseDTO getBoss() {
        return boss;
    }

    public void setBoss(EmployeeResponseDTO boss) {
        this.boss = boss;
    }

    @Override
    public String toString() {
        return "EmployeeResponseDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bossId='" + bossId + '\'' +
            '}';
    }
}
