package com.diego.homebroker.api.v1.controller;

import com.diego.homebroker.api.v1.BaseIT;
import com.diego.homebroker.api.v1.dto.user.UserCreateDTO;
import com.diego.homebroker.api.v1.dto.user.UserResponseDTO;
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
    public void whenFindUserByCpfThenReturnUser() {
        createUser("11111111111");
        UserResponseDTO userResponseDTO = restTemplate.getForObject(
                "http://localhost:" + port + USER_PATH + "11111111111", UserResponseDTO.class);
        assertEquals("11111111111", userResponseDTO.getCpf());
    }

    @Test
    public void whenFindUserByInvalidCpfThenReturnNotFound() {
        ResponseEntity<UserResponseDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + USER_PATH + "33333333333", UserResponseDTO.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateUserThenReturnUser() {
        ResponseEntity<UserResponseDTO> responseEntity = createUser("22222222222");
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals("Some Name", responseEntity.getBody().getName());
    }

    @Test
    public void whenCreateUserWithoutNameThenReturnException() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        ResponseEntity<UserResponseDTO> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + USER_PATH, userCreateDTO, UserResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    private ResponseEntity<UserResponseDTO> createUser(String cpf) {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("Some Name")
                .cpf(cpf)
                .build();
        return restTemplate.postForEntity("http://localhost:" + port + USER_PATH, userCreateDTO,
                UserResponseDTO.class);
    }
}
