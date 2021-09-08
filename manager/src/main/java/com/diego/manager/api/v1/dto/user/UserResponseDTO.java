package com.diego.manager.api.v1.dto.user;

import com.diego.manager.api.v1.dto.contact.ContactDTO;
import com.diego.manager.api.v1.dto.tenant.TenantResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value = "UserResponse")
public class UserResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("User id")
    private String id;
    @ApiModelProperty("User name")
    private String name;
    @ApiModelProperty("User address")
    private String address;
    @ApiModelProperty("User login")
    private String username;
    @ApiModelProperty("User contacts")
    private Set<ContactDTO> contacts = new HashSet<>();
    @ApiModelProperty("User tenant")
    private TenantResponseDTO tenant;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    public TenantResponseDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantResponseDTO tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
            '}';
    }
}
