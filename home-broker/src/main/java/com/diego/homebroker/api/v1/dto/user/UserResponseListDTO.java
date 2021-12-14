package com.diego.homebroker.api.v1.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "UserResponseList")
public class UserResponseListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of users")
    private List<UserResponseDTO> users;

    public UserResponseListDTO() { }

    public UserResponseListDTO(List<UserResponseDTO> users) {
        this.users = users;
    }

    public List<UserResponseDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponseDTO> users) {
        this.users = users;
    }
}