package com.example.rabbitproject.domain;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "TB_USER")
public class User implements Serializable {

    @Id
    @Column(name = "ID", length = 100, nullable = false)
    private String id;
    @Column(name = "NAME", nullable = false)
    @NotBlank(message = "Nome é um campo obrigatório")
    @NotNull(message = "Nome é um campo obrigatório")
    private String name;
    @Column(name = "ADDRESS", nullable = false)
    @NotBlank(message = "Endereço é um campo obrigatório")
    @NotNull(message = "Endereço é um campo obrigatório")
    private String address;
    @Column(name = "INTEGRATION_ID", length = 100)
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
}
