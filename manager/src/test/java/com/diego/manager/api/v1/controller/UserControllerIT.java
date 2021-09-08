package com.diego.manager.api.v1.controller;

import com.diego.manager.api.BaseIT;
import com.diego.manager.api.v1.dto.tenant.TenantCreateDTO;
import com.diego.manager.api.v1.dto.tenant.TenantResponseDTO;
import com.diego.manager.api.v1.dto.user.UserCreateDTO;
import com.diego.manager.api.v1.dto.user.UserResponseDTO;
import com.diego.manager.api.v1.dto.user.UserResponseListDTO;
import com.diego.manager.api.v1.dto.user.UserUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT extends BaseIT {

    private final String USER_PATH = "/manager/api/v1/users/";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenFindAllUsersThenReturnUserList() {
        String tenantId = createTenant();
        createUser("Some Name", "Some Address", tenantId);
        createUser("Another Name", "Another Address", tenantId);
        UserResponseListDTO userResponseListDTO = this.restTemplate
            .getForObject("http://localhost:" + port + USER_PATH, UserResponseListDTO.class);
        assertEquals(2, userResponseListDTO.getUsers().size());
    }

    @Test
    public void whenFindUserByIdThenReturnUserList() {
        String tenantId = createTenant();
        ResponseEntity<UserResponseDTO> responseEntity = createUser("Some Name", "Some Address", tenantId);
        UserResponseDTO userResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                USER_PATH + Objects.requireNonNull(responseEntity.getBody()).getId(), UserResponseDTO.class);
        assertNotNull(userResponseDTO.getId());
    }

    @Test
    public void whenCreateUserThenReturnUser() {
        String tenantId = createTenant();
        ResponseEntity<UserResponseDTO> responseEntity = createUser(
                "Some Name", "Some Address", tenantId);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals("Some Name", responseEntity.getBody().getName());
        assertEquals("Some Address", responseEntity.getBody().getAddress());
        assertEquals(tenantId, responseEntity.getBody().getTenant().getId());
    }

    @Test
    public void whenCreateUserWithoutNameThenReturnException() {
        String tenantId = createTenant();
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .address("Some Address")
                .username("username_" + System.currentTimeMillis())
                .password("password_" + System.currentTimeMillis())
                .tenantId(tenantId)
                .build();
        ResponseEntity<UserResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + USER_PATH, userCreateDTO, UserResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateUserWithoutAddressThenReturnException() {
        String tenantId = createTenant();
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("Some Nome")
                .username("username_" + System.currentTimeMillis())
                .password("password_" + System.currentTimeMillis())
                .tenantId(tenantId)
                .build();
        ResponseEntity<UserResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + USER_PATH, userCreateDTO, UserResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateUserWithoutTenantThenReturnException() {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("Some Nome")
                .username("username_" + System.currentTimeMillis())
                .password("password_" + System.currentTimeMillis())
                .address("Some Address")
                .build();
        ResponseEntity<UserResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + USER_PATH, userCreateDTO, UserResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateUserWithTenantButNotFoundThenReturnException() {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("Some Nome")
                .address("Some Address")
                .username("username_" + System.currentTimeMillis())
                .password("password_" + System.currentTimeMillis())
                .tenantId("invalid-tenant-id")
                .build();
        ResponseEntity<UserResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + USER_PATH, userCreateDTO, UserResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenUpdateUserThenReturnUser() {
        String tenantId = createTenant();
        ResponseEntity<UserResponseDTO> responseEntity = createUser("Some Name", "Some Address", tenantId);
        UserResponseDTO userResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                USER_PATH + Objects.requireNonNull(responseEntity.getBody()).getId(), UserResponseDTO.class);
        assertNotNull(userResponseDTO.getId());

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.Builder.of()
                .name("Edited Name")
                .address(userResponseDTO.getAddress())
                .build();

        Map<String, String> params = new HashMap<>();
        params.put("id", userResponseDTO.getId());
        this.restTemplate.put("http://localhost:" + port + USER_PATH + "/{id}",
                userUpdateDTO, params);

        UserResponseDTO editedResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                USER_PATH + responseEntity.getBody().getId(), UserResponseDTO.class);
        assertNotNull(editedResponseDTO.getId());
        assertEquals(editedResponseDTO.getName(), "Edited Name");
    }

    @Test
    public void whenDeleteUserThenReturnNoContent() {
        String tenantId = createTenant();
        ResponseEntity<UserResponseDTO> responseEntity = createUser(
                "Some Name", "Some Address", tenantId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity result = restTemplate.exchange("http://localhost:" + port + USER_PATH +
            Objects.requireNonNull(responseEntity.getBody()).getId(), HttpMethod.DELETE, entity, ResponseEntity.class);
        assertEquals(204, result.getStatusCodeValue());
    }

    private ResponseEntity<UserResponseDTO> createUser(String name, String address, String tenantId) {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name(name)
                .address(address)
                .tenantId(tenantId)
                .username("username_" + System.currentTimeMillis())
                .password("password_" + System.currentTimeMillis())
                .build();
        return this.restTemplate.postForEntity("http://localhost:" + port + USER_PATH, userCreateDTO,
                UserResponseDTO.class);
    }

    private String createTenant() {
        TenantCreateDTO tenant = TenantCreateDTO.Builder.of()
                .name("Some tenant")
                .build();
        return Objects.requireNonNull(this.restTemplate.postForEntity("http://localhost:" + port +
                        "/manager/api/v1/tenants/", tenant, TenantResponseDTO.class).getBody()).getId();
    }
}
