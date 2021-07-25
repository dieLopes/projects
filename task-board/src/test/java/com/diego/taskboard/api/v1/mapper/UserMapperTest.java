package com.diego.taskboard.api.v1.mapper;

import com.diego.taskboard.api.v1.dto.user.UserCreateDTO;
import com.diego.taskboard.api.v1.dto.user.UserResponseDTO;
import com.diego.taskboard.builder.UserBuilder;
import com.diego.taskboard.api.v1.dto.user.UserUpdateDTO;
import com.diego.taskboard.builder.TenantBuilder;
import com.diego.taskboard.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @Test
    public void whenConvertCreateDTOToEntityThenReturn () {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("Some Name");
        userCreateDTO.setAddress("Some Address");
        User user = UserMapper.createDtoToEntity(userCreateDTO);
        assertThat(user.getId()).isNullOrEmpty();
        assertThat(user.getName()).isEqualTo(user.getName());
        assertThat(user.getAddress()).isEqualTo(user.getAddress());
    }

    @Test
    public void whenConvertUpdateDTOToEntityThenReturn () {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName("Some Name");
        userUpdateDTO.setAddress("Some Address");
        User user = UserMapper.updateDtoToEntity(userUpdateDTO);
        assertThat(user.getId()).isNullOrEmpty();
        assertThat(user.getName()).isEqualTo(user.getName());
        assertThat(user.getAddress()).isEqualTo(user.getAddress());
    }

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        User user = UserBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .tenant(TenantBuilder.of()
                        .id("some-tenant-id")
                        .build())
                .build();
        UserResponseDTO userResponseDTO = UserMapper.entityToDTO(user);
        assertThat(userResponseDTO.getId()).isEqualTo(user.getId());
        assertThat(userResponseDTO.getName()).isEqualTo(user.getName());
        assertThat(userResponseDTO.getAddress()).isEqualTo(user.getAddress());
        assertThat(userResponseDTO.getTenant().getId()).isEqualTo("some-tenant-id");
    }

    @Test
    public void whenConvertEntitiesToListDTOThenReturn () {
        List<User> users = List.of(
            UserBuilder.of()
                    .id("some-id")
                    .name("Some Name")
                    .address("Some Address")
                    .tenant(TenantBuilder.of()
                            .id("some-tenant-id-1")
                            .build())
                    .build(),
            UserBuilder.of()
                    .id("another-id")
                    .name("Another Name")
                    .address("Another Address")
                    .tenant(TenantBuilder.of()
                            .id("some-tenant-id-2")
                            .build())
                    .build());
        List<UserResponseDTO> userResponseDTOS = UserMapper.entitiesToDTOs(users);
        assertThat(userResponseDTOS).hasSize(2)
            .extracting(UserResponseDTO::getId,
                    UserResponseDTO::getName,
                    UserResponseDTO::getAddress)
            .containsExactlyInAnyOrder(
                tuple("some-id", "Some Name", "Some Address"),
                tuple("another-id", "Another Name", "Another Address")
            );
    }
}
