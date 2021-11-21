package com.inter.desafiointer.api.v1.controller;

import com.inter.desafiointer.api.v1.dto.company.CompanyCreateDTO;
import com.inter.desafiointer.api.v1.dto.company.CompanyResponseDTO;
import com.inter.desafiointer.api.v1.dto.company.CompanyResponseListDTO;
import com.inter.desafiointer.api.v1.dto.company.CompanyUpdateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import com.inter.desafiointer.api.v1.dto.user.UserResponseDTO;
import com.inter.desafiointer.api.v1.mapper.CompanyMapper;
import com.inter.desafiointer.api.v1.mapper.UserMapper;
import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.service.CompanyPersistenceService;
import com.inter.desafiointer.service.CompanySearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "Company Service")
@RestController
@RequestMapping(ApiPath.API_PATH + "/companies")
public class CompanyController {

    private final CompanySearchService companySearchService;
    private final CompanyPersistenceService companyPersistenceService;

    public CompanyController(CompanySearchService companySearchService,
                             CompanyPersistenceService companyPersistenceService) {
        this.companySearchService = companySearchService;
        this.companyPersistenceService = companyPersistenceService;
    }

    @ApiOperation(value = "Return all companies")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request OK"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<CompanyResponseListDTO> find (
            @ApiParam(value = "Company status", allowableValues = "ACTIVE,INACTIVE")
            @RequestParam(value = "status", required = false) String status) {
        return ResponseEntity.ok(new CompanyResponseListDTO(
                CompanyMapper.entitiesToDTOs(companySearchService.find(status))));
    }

    @ApiOperation(value = "Return company by code")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(path = "/{code}", produces="application/json")
    public ResponseEntity<CompanyResponseDTO> findByCode (
            @ApiParam(value = "Company code", required = true)
            @NonNull @PathVariable(value = "code") String code) {
        return ResponseEntity.ok(CompanyMapper.entityToDTO(companySearchService.findByCode(code)));
    }

    @ApiOperation(value = "Create company")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Register was created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PostMapping(produces="application/json", consumes="application/json")
    public ResponseEntity<CompanyResponseDTO> create (@RequestBody CompanyCreateDTO userCreateDTO) {
        Company company = companyPersistenceService.save(CompanyMapper.createDtoToEntity(userCreateDTO));
        return new ResponseEntity<>(CompanyMapper.entityToDTO(company), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit company")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity<CompanyResponseDTO> update (
            @ApiParam(value = "Company identification", required = true)
            @NonNull @PathVariable(value = "id") String id,
            @RequestBody CompanyUpdateDTO companyUpdateDTO) {
        Company company = companyPersistenceService.update(id, CompanyMapper.updateDtoToEntity(companyUpdateDTO));
        return ResponseEntity.ok(CompanyMapper.entityToDTO(company));
    }

    @ApiOperation(value = "Replace status and/or price of the company")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PatchMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity<CompanyResponseDTO> patch (
            @RequestBody Map<String, String> patch,
            @ApiParam(value = "Company identification", required = true)
            @PathVariable("id") String id) {
        Company company = companyPersistenceService.patch(patch, id);
        return ResponseEntity.ok(CompanyMapper.entityToDTO(company));
    }

    @ApiOperation(value = "Remove company")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete (
            @ApiParam(value = "Company identification", required = true)
            @PathVariable(value = "id") String id) {
        companyPersistenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}