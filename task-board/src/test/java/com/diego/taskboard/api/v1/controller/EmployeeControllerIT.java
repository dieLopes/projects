package com.diego.taskboard.api.v1.controller;

import com.diego.taskboard.api.v1.dto.employee.EmployeeResponseDTO;
import com.diego.taskboard.api.v1.dto.employee.EmployeeResponseListDTO;
import com.diego.taskboard.api.v1.dto.employee.EmployeeCreateDTO;
import com.diego.taskboard.api.v1.dto.employee.EmployeeUpdateDTO;
import com.diego.taskboard.api.v1.dto.tenant.TenantCreateDTO;
import com.diego.taskboard.api.v1.dto.tenant.TenantResponseDTO;
import com.mongodb.BasicDBObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
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
public class EmployeeControllerIT {

    @Container
    final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String EMPLOYEE_PATH = "/api/v1/employees/";

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
    public void whenFindAllEmployeesThenReturnEmployeeList() {
        String tenantId = createTenant("Some tenant");
        createEmployee("Some Name", "Some Address", tenantId);
        createEmployee("Another Name", "Another Address", tenantId);
        EmployeeResponseListDTO employeeResponseListDTO = this.restTemplate
            .getForObject("http://localhost:" + port + EMPLOYEE_PATH, EmployeeResponseListDTO.class);
        assertEquals(2, employeeResponseListDTO.getEmployees().size());
    }

    @Test
    public void whenFindEmployeeByIdThenReturnEmployeeList() {
        String tenantId = createTenant("Some tenant");
        ResponseEntity<EmployeeResponseDTO> responseEntity = createEmployee("Some Name", "Some Address", tenantId);
        EmployeeResponseDTO employeeResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                EMPLOYEE_PATH + responseEntity.getBody().getId(), EmployeeResponseDTO.class);
        assertNotNull(employeeResponseDTO.getId());
    }

    @Test
    public void whenCreateEmployeeThenReturnEmployee() {
        String tenantId = createTenant("Some tenant");
        ResponseEntity<EmployeeResponseDTO> responseEntity = createEmployee(
                "Some Name", "Some Address", tenantId);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals("Some Name", responseEntity.getBody().getName());
        assertEquals("Some Address", responseEntity.getBody().getAddress());
        assertEquals(tenantId, responseEntity.getBody().getTenant().getId());
    }

    @Test
    public void whenCreateEmployeeWithoutNameThenReturnException() {
        String tenantId = createTenant("Some tenant");
        EmployeeCreateDTO employee = EmployeeCreateDTO.Builder.of()
                .address("Some Address")
                .tenantId(tenantId)
                .build();
        ResponseEntity<EmployeeResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + EMPLOYEE_PATH, employee, EmployeeResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateEmployeeWithoutAddressThenReturnException() {
        String tenantId = createTenant("Some tenant");
        EmployeeCreateDTO employee = EmployeeCreateDTO.Builder.of()
                .name("Some Nome")
                .tenantId(tenantId)
                .build();
        ResponseEntity<EmployeeResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + EMPLOYEE_PATH, employee, EmployeeResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateEmployeeWithoutTenantThenReturnException() {
        EmployeeCreateDTO employee = EmployeeCreateDTO.Builder.of()
                .name("Some Nome")
                .address("Some Address")
                .build();
        ResponseEntity<EmployeeResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + EMPLOYEE_PATH, employee, EmployeeResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateEmployeeWithTenantButNotFoundThenReturnException() {
        EmployeeCreateDTO employee = EmployeeCreateDTO.Builder.of()
                .name("Some Nome")
                .address("Some Address")
                .tenantId("invalid-tenant-id")
                .build();
        ResponseEntity<EmployeeResponseDTO> responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + EMPLOYEE_PATH, employee, EmployeeResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenUpdateEmployeeThenReturnEmployee() {
        String tenantId = createTenant("Some tenant");
        ResponseEntity<EmployeeResponseDTO> responseEntity = createEmployee("Some Name", "Some Address", tenantId);
        EmployeeResponseDTO employeeResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                EMPLOYEE_PATH + responseEntity.getBody().getId(), EmployeeResponseDTO.class);
        assertNotNull(employeeResponseDTO.getId());

        EmployeeUpdateDTO employeeUpdateDTO = EmployeeUpdateDTO.Builder.of()
                .name("Edited Name")
                .address(employeeResponseDTO.getAddress())
                .build();

        Map<String, String> params = new HashMap<>();
        params.put("id", employeeResponseDTO.getId());
        this.restTemplate.put("http://localhost:" + port + EMPLOYEE_PATH + "/{id}",
                employeeUpdateDTO, params);

        EmployeeResponseDTO editedResponseDTO = this.restTemplate.getForObject("http://localhost:" + port +
                EMPLOYEE_PATH + responseEntity.getBody().getId(), EmployeeResponseDTO.class);
        assertNotNull(editedResponseDTO.getId());
        assertEquals(editedResponseDTO.getName(), "Edited Name");
    }

    @Test
    public void whenDeleteEmployeeThenReturnNoContent() {
        String tenantId = createTenant("Some tenant");
        ResponseEntity<EmployeeResponseDTO> responseEntity = createEmployee(
                "Some Name", "Some Address", tenantId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity result = restTemplate.exchange("http://localhost:" + port + EMPLOYEE_PATH +
            responseEntity.getBody().getId(), HttpMethod.DELETE, entity, ResponseEntity.class);
        assertEquals(204, result.getStatusCodeValue());
    }

    private ResponseEntity<EmployeeResponseDTO> createEmployee (String name, String address, String tenantId) {
        EmployeeCreateDTO employee = EmployeeCreateDTO.Builder.of()
                .name(name)
                .address(address)
                .tenantId(tenantId)
                .build();
        return this.restTemplate.postForEntity("http://localhost:" + port + EMPLOYEE_PATH, employee,
                EmployeeResponseDTO.class);
    }

    private String createTenant (String name) {
        TenantCreateDTO tenant = TenantCreateDTO.Builder.of()
                .name(name)
                .build();
        return this.restTemplate.postForEntity("http://localhost:" + port + "/api/v1/tenants/", tenant,
                TenantResponseDTO.class).getBody().getId();
    }
}
