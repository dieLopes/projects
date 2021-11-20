package com.inter.desafiointer.api.v1.controller;

import com.inter.desafiointer.api.v1.BaseIT;
import com.inter.desafiointer.api.v1.dto.company.CompanyCreateDTO;
import com.inter.desafiointer.api.v1.dto.company.CompanyResponseDTO;
import com.inter.desafiointer.api.v1.dto.company.CompanyResponseListDTO;
import com.inter.desafiointer.api.v1.dto.company.CompanyUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerIT extends BaseIT {

    private final String COMPANY_PATH = "/broker/api/v1/companies/";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenFindAllCompaniesThenReturnCompanyList() {
        CompanyResponseListDTO companyResponseListDTO = restTemplate.getForObject(
                "http://localhost:" + port + COMPANY_PATH, CompanyResponseListDTO.class);
        assertEquals(7, companyResponseListDTO.getCompanies().size());
    }

    @Test
    public void whenFindCompaniesByActiveStatusThenReturnCompanyList() {
        CompanyResponseListDTO companyResponseListDTO = restTemplate.getForObject(
                "http://localhost:" + port + COMPANY_PATH + "?status=ACTIVE", CompanyResponseListDTO.class);
        assertEquals(7, companyResponseListDTO.getCompanies().size());
    }

    @Test
    public void whenFindCompaniesByInactiveStatusThenReturnCompanyList() {
        CompanyResponseListDTO companyResponseListDTO = restTemplate.getForObject(
                "http://localhost:" + port + COMPANY_PATH + "?status=INACTIVE", CompanyResponseListDTO.class);
        assertEquals(0, companyResponseListDTO.getCompanies().size());
    }

    @Test
    public void whenFindCompaniesByInvalidStatusThenReturnBadRequest() {
        ResponseEntity<CompanyResponseListDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + COMPANY_PATH + "?status=INVALID", CompanyResponseListDTO.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenFindCompanyByCodeThenReturnCompany() {
        CompanyResponseDTO companyResponseDTO = restTemplate.getForObject(
                "http://localhost:" + port + COMPANY_PATH + "BIDI11", CompanyResponseDTO.class);
        assertEquals("BIDI11", companyResponseDTO.getCode());
    }

    @Test
    public void whenFindCompaniesByInvalidCodeThenReturnNotFound() {
        ResponseEntity<CompanyResponseDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + COMPANY_PATH + "INVALID", CompanyResponseDTO.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenCreateCompanyThenReturnCompany() {
        CompanyResponseDTO company = createCompany(BigDecimal.TEN);
        assertNotNull(company.getId());
        assertEquals("New Company", company.getName());
        assertEquals("NEW11", company.getCode());
        assertEquals(BigDecimal.TEN, company.getPrice());
        assertEquals("ACTIVE", company.getStatus());

        delete(company.getId());
    }

    @Test
    public void whenUpdateCompanyThenReturnEditedCompany() {
        CompanyResponseDTO companyResponseDTO = createCompany(new BigDecimal(50));
        assertNotNull(companyResponseDTO.getId());

        CompanyUpdateDTO companyUpdateDTO = CompanyUpdateDTO.Builder.of()
                .name(companyResponseDTO.getName() + " EDIT")
                .code(companyResponseDTO.getCode() + "EDIT")
                .price(companyResponseDTO.getPrice().add(new BigDecimal("500.00")))
                .status("INACTIVE")
                .build();

        Map<String, String> params = Map.of("id", companyResponseDTO.getId());
        restTemplate.put("http://localhost:" + port + COMPANY_PATH + "{id}",
                companyUpdateDTO, params);

        CompanyResponseDTO editedResponseDTO = restTemplate.getForObject(
                "http://localhost:" + port + COMPANY_PATH + companyUpdateDTO.getCode(), CompanyResponseDTO.class);
        assertNotNull(editedResponseDTO.getId());
        assertEquals(editedResponseDTO.getName(), companyUpdateDTO.getName());
        assertEquals(editedResponseDTO.getCode(), companyUpdateDTO.getCode());
        assertEquals(editedResponseDTO.getPrice(), companyUpdateDTO.getPrice());
        assertEquals(editedResponseDTO.getStatus(), companyUpdateDTO.getStatus());

        delete(companyResponseDTO.getId());
    }

    @Test
    public void whenUpdateByInvalidCodeThenReturnNotFound() {
        CompanyUpdateDTO companyUpdateDTO = CompanyUpdateDTO.Builder.of()
                .name("SOME NAME")
                .code("CODE")
                .price(BigDecimal.TEN)
                .status("ACTIVE")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<CompanyUpdateDTO> entity = new HttpEntity<>(companyUpdateDTO, headers);
        ResponseEntity<CompanyResponseDTO> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + COMPANY_PATH + "invalid-code",
                HttpMethod.PUT, entity, CompanyResponseDTO.class);

        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenPatchCompanyThenReturnEditedCompanyAttributes() {
        CompanyResponseDTO companyResponseDTO = createCompany(new BigDecimal(50));
        assertNotNull(companyResponseDTO.getId());
        assertEquals(companyResponseDTO.getStatus(), "ACTIVE");
        assertEquals(companyResponseDTO.getPrice(), new BigDecimal(50));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(Map.of(
                "status", "INACTIVE",
                "price", BigDecimal.TEN),
                headers);
        CompanyResponseDTO patchCompany = restTemplate.patchForObject("http://localhost:" + port +
                COMPANY_PATH + companyResponseDTO.getId(), entity, CompanyResponseDTO.class);

        assertEquals(patchCompany.getId(), companyResponseDTO.getId());
        assertEquals(patchCompany.getStatus(), "INACTIVE");
        assertEquals(patchCompany.getPrice(), BigDecimal.TEN);

        delete(companyResponseDTO.getId());
    }

    @Test
    public void whenPatchCompanyWithInvalidIdThenReturnNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(Map.of("status", "ACTIVE"), headers);
        ResponseEntity<CompanyResponseDTO> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + COMPANY_PATH + "invalid-code",
                HttpMethod.PATCH, entity, CompanyResponseDTO.class);

        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void whenDeleteCompanyThenReturnNoContent() {
        CompanyResponseDTO companyResponseDTO = createCompany(new BigDecimal(50));
        assertNotNull(companyResponseDTO.getId());
        delete(companyResponseDTO.getId());
        ResponseEntity<CompanyResponseDTO> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + COMPANY_PATH + "NEW11", CompanyResponseDTO.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    private CompanyResponseDTO createCompany(BigDecimal price) {
        CompanyCreateDTO companyCreateDTO = CompanyCreateDTO.Builder.of()
                .name("New Company")
                .code("NEW11")
                .price(price)
                .build();
        return restTemplate.postForEntity("http://localhost:" + port + COMPANY_PATH, companyCreateDTO,
                CompanyResponseDTO.class).getBody();
    }

    private void delete (String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        restTemplate.exchange("http://localhost:" + port + COMPANY_PATH + id,
                HttpMethod.DELETE, entity, ResponseEntity.class);
    }
}
