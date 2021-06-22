package com.example.rabbitproject.domain;

import javax.persistence.*;
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
    @NotBlank(message = "Name is the mandatory field")
    @NotNull(message = "Name is the mandatory field")
    private String name;
    @Column(name = "ADDRESS", nullable = false)
    @NotBlank(message = "Address is the mandatory field")
    @NotNull(message = "Address is the mandatory field")
    private String address;
    @Column(name = "INTEGRATION_ID", length = 100)
    private String integrationId;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StatusEnum status;

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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
