package com.diego.taskboard.api.v1.dto.user;

import com.diego.taskboard.domain.Contact;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value = "UserCreate")
public class UserCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "User name", required = true)
    private String name;
    @ApiModelProperty(value = "User address", required = true)
    private String address;
    @ApiModelProperty(value = "User login", required = true)
    private String username;
    @ApiModelProperty(value = "User password", required = true)
    private String password;
    @ApiModelProperty(value = "List of user contact", required = true)
    private Set<Contact> contacts = new HashSet<>();
    @ApiModelProperty(value = "User tenant id", required = true)
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tenantId='" + tenantId + '\'' +
            '}';
    }

    public static class Builder {

        private final UserCreateDTO user;

        private Builder() {
            user = new UserCreateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder name (String name) {
            user.name = name;
            return this;
        }

        public Builder address (String address) {
            user.address = address;
            return this;
        }

        public Builder contacts (Set<Contact> contacts) {
            user.contacts = contacts;
            return this;
        }

        public Builder tenantId (String tenantId) {
            user.tenantId = tenantId;
            return this;
        }

        public UserCreateDTO build () {
            return user;
        }
    }
}