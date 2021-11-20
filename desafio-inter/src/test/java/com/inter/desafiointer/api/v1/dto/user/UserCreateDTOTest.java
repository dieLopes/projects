package com.inter.desafiointer.api.v1.dto.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateDTOTest {

    @Test
    public void whenBuildDTOThenReturnDTO () {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("Some User")
                .build();
        assertThat(userCreateDTO.getName()).isEqualTo("Some User");
    }
}
