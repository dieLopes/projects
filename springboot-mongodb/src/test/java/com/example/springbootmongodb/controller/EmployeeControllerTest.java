package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.controller.dto.employee.EmployeeCreateDTO;
import com.example.springbootmongodb.controller.dto.employee.EmployeeResponseDTO;
import com.example.springbootmongodb.controller.dto.employee.EmployeeResponseListDTO;
import com.mongodb.BasicDBObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application-test.properties")
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String EMPLOYEE_PATH = "/api/employees/";

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
        createEmployee("Some Name", "Some Address");
        createEmployee("Another Name", "Another Address");
        EmployeeResponseListDTO employeeResponseListDTO = this.restTemplate
            .getForObject("http://localhost:" + port + EMPLOYEE_PATH, EmployeeResponseListDTO.class);
        assertEquals(2, employeeResponseListDTO.getEmployees().size());
    }

    @Test
    public void whenFindEmployeeByIdThenReturnEmployeeList() {
        ResponseEntity<EmployeeResponseDTO> responseEntity = createEmployee("Some Name", "Some Address");
        EmployeeResponseDTO employeeResponseDTO = this.restTemplate.getForObject(
            "http://localhost:" + port + EMPLOYEE_PATH + responseEntity.getBody().getId(), EmployeeResponseDTO.class);
        assertEquals(responseEntity.getBody().getId(), employeeResponseDTO.getId());
    }

    @Test
    public void whenCreateEmployeeThenReturnEmployee() {
        ResponseEntity<EmployeeResponseDTO> responseEntity = createEmployee("Some Name", "Some Address");
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals("Some Name", responseEntity.getBody().getName());
        assertEquals("Some Address", responseEntity.getBody().getAddress());
    }

    @Test
    public void whenCreateEmployeeWithoutNameThenReturnEmployee() {
        EmployeeCreateDTO employee = new EmployeeCreateDTO();
        employee.setAddress("Some Address");
        ResponseEntity<EmployeeResponseDTO> responseEntity = this.restTemplate.postForEntity(
            "http://localhost:" + port + EMPLOYEE_PATH, employee, EmployeeResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateEmployeeWithoutAddressThenReturnEmployee() {
        EmployeeCreateDTO employee = new EmployeeCreateDTO();
        employee.setName("Some Name");
        ResponseEntity<EmployeeResponseDTO> responseEntity = this.restTemplate.postForEntity(
            "http://localhost:" + port + EMPLOYEE_PATH, employee, EmployeeResponseDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenDeleteEmployeeThenReturnNoContent() {
        ResponseEntity<EmployeeResponseDTO> responseEntity = createEmployee("Some Name", "Some Address");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity result = restTemplate.exchange("http://localhost:" + port + EMPLOYEE_PATH +
            responseEntity.getBody().getId(), HttpMethod.DELETE, entity, ResponseEntity.class);
        assertEquals(204, result.getStatusCodeValue());
    }

    private ResponseEntity<EmployeeResponseDTO> createEmployee (String name, String address) {
        EmployeeCreateDTO employee = new EmployeeCreateDTO();
        employee.setName(name);
        employee.setAddress(address);
        return this.restTemplate.postForEntity(
            "http://localhost:" + port + EMPLOYEE_PATH, employee, EmployeeResponseDTO.class);
    }
}
