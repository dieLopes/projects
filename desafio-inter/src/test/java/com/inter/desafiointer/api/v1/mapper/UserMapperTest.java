package com.inter.desafiointer.api.v1.mapper;

import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseDTO;
import com.inter.desafiointer.builder.UserBuilder;
import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class UserMapperTest {

    @Test
    public void whenConvertCreateDTOToEntityThenReturn () {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("Some Name")
                .build();
        User user = UserMapper.createDtoToEntity(userCreateDTO);
        assertThat(user.getId()).isNullOrEmpty();
        assertThat(user.getName()).isEqualTo("Some Name");
        assertThat(user.getWallet()).isNull();
    }

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        User user = UserBuilder.of()
                .id("some-id")
                .name("Some Name")
                .wallet(WalletBuilder.of()
                        .id("wallet-id")
                        .build())
                .build();
        UserResponseDTO userResponseDTO = UserMapper.entityToDTO(user);
        assertThat(userResponseDTO.getId()).isEqualTo(user.getId());
        assertThat(userResponseDTO.getName()).isEqualTo(user.getName());
        assertThat(userResponseDTO.getWallet().getId()).isEqualTo(user.getWallet().getId());
    }

    @Test
    public void whenConvertEntitiesToListDTOThenReturn () {
        List<User> users = List.of(
            UserBuilder.of()
                    .id("some-id")
                    .name("Some Name")
                    .wallet(WalletBuilder.of()
                            .id("wallet-id")
                            .build())
                    .build(),
            UserBuilder.of()
                    .id("another-id")
                    .name("Another Name")
                    .wallet(WalletBuilder.of()
                            .id("another-wallet-id")
                            .build())
                    .build());
        List<UserResponseDTO> userResponseDTOS = UserMapper.entitiesToDTOs(users);
        assertThat(userResponseDTOS).hasSize(2)
            .extracting(UserResponseDTO::getId,
                    UserResponseDTO::getName)
            .containsExactlyInAnyOrder(
                tuple("some-id", "Some Name"),
                tuple("another-id", "Another Name")
            );
    }
}
