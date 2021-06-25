package com.example.springbootpostgresql.controller.dto;

import java.io.Serializable;

public class UserCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String integrationId;

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

    public String getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(String integrationId) {
        this.integrationId = integrationId;
    }

    @Override
    public String toString() {
        return "UserCreateDTO{" +
            "name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", integrationId='" + integrationId + '\'' +
            '}';
    }
}
