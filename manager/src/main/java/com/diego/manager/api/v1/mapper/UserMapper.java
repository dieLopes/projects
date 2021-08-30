package com.diego.manager.api.v1.mapper;

import com.diego.manager.api.v1.dto.user.UserCreateDTO;
import com.diego.manager.api.v1.dto.user.UserResponseDTO;
import com.diego.manager.api.v1.dto.user.UserUpdateDTO;
import com.diego.manager.builder.TenantBuilder;
import com.diego.manager.domain.User;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static User createDtoToEntity(UserCreateDTO userCreateDTO) {
        User user = mapper.map(userCreateDTO, User.class);
        user.setTenant(TenantBuilder.of().id(userCreateDTO.getTenantId()).build());
        return user;
    }

    public static User updateDtoToEntity (UserUpdateDTO userUpdateDTO) {
        return mapper.map(userUpdateDTO, User.class);
    }

    public static UserResponseDTO entityToDTO(User user) {
        UserResponseDTO userResponseDTO = mapper.map(user, UserResponseDTO.class);
        userResponseDTO.setTenant(TenantMapper.entityToDTO(user.getTenant()));
        return userResponseDTO;
    }

    public static List<UserResponseDTO> entitiesToDTOs (List<User> users) {
        return users
            .stream()
            .map(UserMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
