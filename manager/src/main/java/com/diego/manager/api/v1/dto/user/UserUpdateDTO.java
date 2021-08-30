package com.diego.manager.api.v1.dto.user;

import com.diego.manager.api.v1.dto.contact.ContactDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ApiModel(value = "UserUpdate")
public class UserUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "User name")
    private String name;
    @ApiModelProperty(value = "User address")
    private String address;
    @ApiModelProperty("User contacts")
    private Set<ContactDTO> contacts = new HashSet<>();

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

    public Set<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(Set<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "UserUpdateDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
            '}';
    }

    public static class Builder {

        private final UserUpdateDTO user;

        private Builder() {
            user = new UserUpdateDTO();
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

        public UserUpdateDTO build () {
            return user;
        }
    }
}
