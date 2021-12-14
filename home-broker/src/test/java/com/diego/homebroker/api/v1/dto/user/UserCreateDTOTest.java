package com.diego.homebroker.api.v1.dto.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateDTOTest {

    @Test
    public void whenBuildDTOThenReturnDTO () {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("Some User")
                .cpf("11111111111")
                .build();
        assertThat(userCreateDTO.getName()).isEqualTo("Some User");
        assertThat(userCreateDTO.getCpf()).isEqualTo("11111111111");
    }
}
