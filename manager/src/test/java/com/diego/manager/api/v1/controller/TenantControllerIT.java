package com.diego.manager.api.v1.controller;

import com.diego.manager.api.v1.dto.tenant.TenantCreateDTO;
import com.diego.manager.api.v1.dto.tenant.TenantResponseDTO;
import com.diego.manager.api.v1.dto.tenant.TenantResponseListDTO;
import com.diego.manager.api.v1.dto.tenant.TenantUpdateDTO;
import com.mongodb.BasicDBObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class TenantControllerIT {

    @Container
    final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String TENANT_PATH = "/api/v1/tenants/";

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
    public void whenFindAllTenantsThenReturnTenantList() {
        createTenant("Some Tenant");
        createTenant("Another Tenant");
        TenantResponseListDTO tenantResponseListDTO = this.restTemplate
            .getForObject("http://localhost:" + port + TENANT_PATH, TenantResponseListDTO.class);
        assertEquals(2, tenantResponseListDTO.getTenants().size());
    }

    @Test
    public void whenFindTenantByIdThenReturnTenantList() {
        ResponseEntity<TenantResponseDTO> responseEntity = createTenant("Some Tenant");
        TenantResponseDTO tenantResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                TENANT_PATH + responseEntity.getBody().getId(), TenantResponseDTO.class);
        assertNotNull(tenantResponseDTO.getId());
    }

    @Test
    public void whenCreateTenantThenReturnTenant() {
        ResponseEntity<TenantResponseDTO> responseEntity = createTenant("Some Tenant");
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals("Some Tenant", responseEntity.getBody().getName());
    }

    @Test
    public void whenCreateTenantWithoutNameThenReturnException() {
        TenantCreateDTO tenant = TenantCreateDTO.Builder.of().build();
        ResponseEntity<TenantResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + TENANT_PATH, tenant, TenantResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenUpdateTenantThenReturnTenant() {
        ResponseEntity<TenantResponseDTO> responseEntity = createTenant("Some Tenant");
        TenantResponseDTO response = this.restTemplate.getForObject("http://localhost:" + port +
                TENANT_PATH + responseEntity.getBody().getId(), TenantResponseDTO.class);
        assertNotNull(response.getId());

        TenantUpdateDTO updateDTO = TenantUpdateDTO.Builder.of()
                .name("Edited Name")
                .build();

        Map<String, String> params = new HashMap<>();
        params.put("id", response.getId());
        this.restTemplate.put("http://localhost:" + port + TENANT_PATH + "/{id}", updateDTO, params);

        TenantResponseDTO editedResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                TENANT_PATH + responseEntity.getBody().getId(), TenantResponseDTO.class);
        assertNotNull(editedResponseDTO.getId());
        assertEquals(editedResponseDTO.getName(), "Edited Name");
    }

    private ResponseEntity<TenantResponseDTO> createTenant (String name) {
        TenantCreateDTO tenant = TenantCreateDTO.Builder.of()
                .name(name)
                .build();
        return this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/tenants/", tenant,
                TenantResponseDTO.class);
    }
}
