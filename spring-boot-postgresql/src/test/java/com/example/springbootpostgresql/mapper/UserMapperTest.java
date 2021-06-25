package com.example.springbootpostgresql.mapper;

import com.example.springbootpostgresql.builder.UserBuilder;
import com.example.springbootpostgresql.controller.dto.UserCreateDTO;
import com.example.springbootpostgresql.controller.dto.UserResponseDTO;
import com.example.springbootpostgresql.controller.dto.UserUpdateDTO;
import com.example.springbootpostgresql.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.example.springbootpostgresql.domain.StatusEnum.ACTIVE;
import static com.example.springbootpostgresql.domain.StatusEnum.INACTIVE;
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
        assertThat(user.getIntegrationId()).isNullOrEmpty();
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
        assertThat(user.getIntegrationId()).isNullOrEmpty();
    }

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        User user = UserBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .integrationId("some-integration-id")
                .status(ACTIVE)
                .build();
        UserResponseDTO userResponseDTO = UserMapper.entityToDTO(user);
        assertThat(userResponseDTO.getId()).isEqualTo(user.getId());
        assertThat(userResponseDTO.getName()).isEqualTo(user.getName());
        assertThat(userResponseDTO.getAddress()).isEqualTo(user.getAddress());
        assertThat(userResponseDTO.getIntegrationId()).isEqualTo(user.getIntegrationId());
        assertThat(userResponseDTO.getStatus()).isEqualTo(user.getStatus().name());
    }

    @Test
    public void whenConvertEntitiesToListDTOThenReturn () {
        List<User> users = List.of(
            UserBuilder.of()
                    .id("some-id")
                    .name("Some Name")
                    .address("Some Address")
                    .integrationId("some-integration-id")
                    .status(ACTIVE)
                    .build(),
            UserBuilder.of()
                    .id("another-id")
                    .name("Another Name")
                    .address("Another Address")
                    .integrationId("another-integration-id")
                    .status(INACTIVE)
                    .build());
        List<UserResponseDTO> userResponseDTOs = UserMapper.entitiesToDTOs(users);
        assertThat(userResponseDTOs).hasSize(2)
            .extracting(UserResponseDTO::getId,
                    UserResponseDTO::getName,
                    UserResponseDTO::getAddress,
                    UserResponseDTO::getIntegrationId,
                    UserResponseDTO::getStatus)
            .containsExactlyInAnyOrder(
                tuple("some-id", "Some Name", "Some Address", "some-integration-id", "ACTIVE"),
                tuple("another-id", "Another Name", "Another Address", "another-integration-id", "INACTIVE")
            );
    }
}
