package com.diego.taskboard.api.v1.controller;

import com.diego.taskboard.api.v1.dto.tenant.TenantCreateDTO;
import com.diego.taskboard.api.v1.dto.tenant.TenantResponseDTO;
import com.diego.taskboard.api.v1.dto.user.UserCreateDTO;
import com.diego.taskboard.api.v1.dto.user.UserResponseDTO;
import com.diego.taskboard.api.v1.dto.user.UserResponseListDTO;
import com.diego.taskboard.api.v1.dto.user.UserUpdateDTO;
import com.mongodb.BasicDBObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserControllerIT {

    @Container
    final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String USER_PATH = "/api/v1/users/";

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Before
    public void initDb () {
        for (String collectionName : mongoTemplate.getCollectionNames()) {
            if (!collectionName.startsWith("system.")) {
                mongoTemplate.getCollection(collectionName).deleteMany(new BasicDBObject());
            }
        }
    }

    @Test
    public void whenFindAllUsersThenReturnUserList() {
        String tenantId = createTenant("Some tenant");
        createUser("Some Name", "Some Address", tenantId);
        createUser("Another Name", "Another Address", tenantId);
        UserResponseListDTO userResponseListDTO = this.restTemplate
            .getForObject("http://localhost:" + port + USER_PATH, UserResponseListDTO.class);
        assertEquals(2, userResponseListDTO.getUsers().size());
    }

    @Test
    public void whenFindUserByIdThenReturnUserList() {
        String tenantId = createTenant("Some tenant");
        ResponseEntity<UserResponseDTO> responseEntity = createUser("Some Name", "Some Address", tenantId);
        UserResponseDTO userResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                USER_PATH + responseEntity.getBody().getId(), UserResponseDTO.class);
        assertNotNull(userResponseDTO.getId());
    }

    @Test
    public void whenCreateUserThenReturnUser() {
        String tenantId = createTenant("Some tenant");
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
        String tenantId = createTenant("Some tenant");
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .address("Some Address")
                .tenantId(tenantId)
                .build();
        ResponseEntity<UserResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + USER_PATH, userCreateDTO, UserResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateUserWithoutAddressThenReturnException() {
        String tenantId = createTenant("Some tenant");
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name("Some Nome")
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
                .tenantId("invalid-tenant-id")
                .build();
        ResponseEntity<UserResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + USER_PATH, userCreateDTO, UserResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenUpdateUserThenReturnUser() {
        String tenantId = createTenant("Some tenant");
        ResponseEntity<UserResponseDTO> responseEntity = createUser("Some Name", "Some Address", tenantId);
        UserResponseDTO userResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                USER_PATH + responseEntity.getBody().getId(), UserResponseDTO.class);
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
        String tenantId = createTenant("Some tenant");
        ResponseEntity<UserResponseDTO> responseEntity = createUser(
                "Some Name", "Some Address", tenantId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity result = restTemplate.exchange("http://localhost:" + port + USER_PATH +
            responseEntity.getBody().getId(), HttpMethod.DELETE, entity, ResponseEntity.class);
        assertEquals(204, result.getStatusCodeValue());
    }

    private ResponseEntity<UserResponseDTO> createUser(String name, String address, String tenantId) {
        UserCreateDTO userCreateDTO = UserCreateDTO.Builder.of()
                .name(name)
                .address(address)
                .tenantId(tenantId)
                .build();
        return this.restTemplate.postForEntity("http://localhost:" + port + USER_PATH, userCreateDTO,
                UserResponseDTO.class);
    }

    private String createTenant (String name) {
        TenantCreateDTO tenant = TenantCreateDTO.Builder.of()
                .name(name)
                .build();
        return this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/tenants/", tenant,
                TenantResponseDTO.class).getBody().getId();
    }
}
