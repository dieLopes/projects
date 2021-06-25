package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.controller.dto.UserCreateDTO;
import com.example.springbootmongodb.controller.dto.UserResponseDTO;
import com.example.springbootmongodb.controller.dto.UserResponseListDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class UserControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private final String USER_PATH = "/api/users/";

    @Test
    public void whenFindAllUsersThenReturnUserList() {
        createUser("Some Name", "Some Address");
        createUser("Another Name", "Another Address");
        UserResponseListDTO userResponseListDTO = this.restTemplate
            .getForObject("http://localhost:" + port + USER_PATH, UserResponseListDTO.class);
        assertEquals(2, userResponseListDTO.getUsers().size());
    }

    @Test
    public void whenFindUserByIdThenReturnUserList() {
        ResponseEntity<UserResponseDTO> responseEntity = createUser("Some Name", "Some Address");
        UserResponseDTO userResponseDTO = this.restTemplate.getForObject(
            "http://localhost:" + port + USER_PATH + responseEntity.getBody().getId(), UserResponseDTO.class);
        assertEquals(responseEntity.getBody().getId(), userResponseDTO.getId());
    }

    @Test
    public void whenCreateUserThenReturnUser() {
        ResponseEntity<UserResponseDTO> responseEntity = createUser("Some Name", "Some Address");
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals("Some Name", responseEntity.getBody().getName());
        assertEquals("Some Address", responseEntity.getBody().getAddress());
        assertEquals("ACTIVE", responseEntity.getBody().getStatus());
    }

    @Test
    public void whenCreateUserWithoutNameThenReturnUser() {
        UserCreateDTO user = new UserCreateDTO();
        user.setAddress("Some Address");
        ResponseEntity<UserResponseDTO> responseEntity = this.restTemplate.postForEntity(
            "http://localhost:" + port + USER_PATH, user, UserResponseDTO.class);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateUserWithoutAddressThenReturnUser() {
        UserCreateDTO user = new UserCreateDTO();
        user.setName("Some Name");
        ResponseEntity<UserResponseDTO> responseEntity = this.restTemplate.postForEntity(
            "http://localhost:" + port + USER_PATH, user, UserResponseDTO.class);
        assertEquals(500, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenDeleteUserThenReturnNoContent() {
        ResponseEntity<UserResponseDTO> responseEntity = createUser("Some Name", "Some Address");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity result = restTemplate.exchange("http://localhost:" + port + USER_PATH +
            responseEntity.getBody().getId(), HttpMethod.DELETE, entity, ResponseEntity.class);
        assertEquals(204, result.getStatusCodeValue());
    }

    private ResponseEntity<UserResponseDTO> createUser (String name, String address) {
        UserCreateDTO user = new UserCreateDTO();
        user.setName(name);
        user.setAddress(address);
        return this.restTemplate.postForEntity(
            "http://localhost:" + port + USER_PATH, user, UserResponseDTO.class);
    }
}
