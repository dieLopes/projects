package com.example.springbootmongodb.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class UserResponseListDTO {

    private List<UserResponseDTO> users = new ArrayList<>();

    public UserResponseListDTO () { }

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
