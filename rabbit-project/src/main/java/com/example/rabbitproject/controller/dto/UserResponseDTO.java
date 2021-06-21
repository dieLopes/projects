package com.example.rabbitproject.controller.dto;

import java.io.Serializable;

public class UserResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String address;
    private String integrationId;

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

    public String getIntegrationId() {
        return integrationId;
    }

    public void setIntegrationId(String integrationId) {
        this.integrationId = integrationId;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", integrationId='" + integrationId + '\'' +
            '}';
    }
}
