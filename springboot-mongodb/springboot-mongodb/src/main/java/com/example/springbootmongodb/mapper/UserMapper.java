package com.example.springbootmongodb.mapper;

import com.example.springbootmongodb.controller.dto.UserCreateDTO;
import com.example.springbootmongodb.controller.dto.UserResponseDTO;
import com.example.springbootmongodb.controller.dto.UserUpdateDTO;
import com.example.springbootmongodb.domain.User;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static User createDtoToEntity(UserCreateDTO userCreateDTO) {
        return mapper.map(userCreateDTO, User.class);
    }

    public static User updateDtoToEntity (UserUpdateDTO userUpdateDTO) {
        return mapper.map(userUpdateDTO, User.class);
    }

    public static UserResponseDTO entityToDTO(User user) {
        return mapper.map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> entitiesToDTOs (List<User> users) {
        return users
            .stream()
            .map(UserMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
