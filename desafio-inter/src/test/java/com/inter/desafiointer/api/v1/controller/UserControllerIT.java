package com.inter.desafiointer.api.v1.controller;

import com.inter.desafiointer.api.v1.BaseIT;
import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserControllerIT extends BaseIT {

    private final String USER_PATH = "/broker/api/v1/users/";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenFindAllUsersThenReturnUserList() {
        createUser("Some Name");
        createUser("Another Name");
        UserResponseListDTO userResponseListDTO = restTemplate
            .getForObject("http://localhost:" + port + USER_PATH, UserResponseListDTO.class);
        assertEquals(2, userResponseListDTO.getUsers().size());
    }

    @Test
    public void whenCreateUserThenReturnUser() {
        ResponseEntity<UserResponseDTO> responseEntity = createUser("Some Name");
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getWallet().getId());
        assertEquals("Some Name", responseEntity.getBody().getName());
    }

    @Test
    public void whenCreateUserWithoutNameThenReturnException() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        ResponseEntity<UserResponseDTO> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + USER_PATH, userCreateDTO, UserResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    private ResponseEntity<UserResponseDTO> createUser(String name) {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name(name)
                .build();
        return restTemplate.postForEntity("http://localhost:" + port + USER_PATH, userCreateDTO,
                UserResponseDTO.class);
    }
}
