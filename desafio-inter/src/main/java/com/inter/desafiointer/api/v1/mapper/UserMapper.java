package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseDTO;
import com.inter.desafiointer.domain.User;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static User createDtoToEntity(UserCreateDTO userCreateDTO) {
        return mapper.map(userCreateDTO, User.class);
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
