package com.example.springbootmongodb.api.v1.controller;

import com.example.springbootmongodb.api.v1.dto.tenant.TenantCreateDTO;
import com.example.springbootmongodb.api.v1.dto.tenant.TenantResponseDTO;
import com.example.springbootmongodb.api.v1.dto.tenant.TenantResponseListDTO;
import com.example.springbootmongodb.api.v1.dto.tenant.TenantUpdateDTO;
import com.example.springbootmongodb.api.v1.mapper.TenantMapper;
import com.example.springbootmongodb.domain.Tenant;
import com.example.springbootmongodb.service.TenantPersistenceService;
import com.example.springbootmongodb.service.TenantSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Tenant Service")
@RestController
@RequestMapping(ApiPath.API_PATH + "tenants")
public class TenantController {

    private TenantPersistenceService tenantPersistenceService;
    private TenantSearchService tenantSearchService;

    public TenantController(TenantPersistenceService tenantPersistenceService,
                            TenantSearchService tenantSearchService) {
        this.tenantPersistenceService = tenantPersistenceService;
        this.tenantSearchService = tenantSearchService;
    }

    @ApiOperation(value = "Return tenant by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resquest responses OK"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(value = "/{id}", produces="application/json")
    public ResponseEntity<TenantResponseDTO> getById (@NonNull @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(TenantMapper.entityToDTO(tenantSearchService.findById(id)));
    }

    @ApiOperation(value = "Return all tenants")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resquest responses OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<TenantResponseListDTO> getAll () {
        return ResponseEntity.ok(new TenantResponseListDTO(TenantMapper.entitiesToDTOs(tenantSearchService.findAll())));
    }

    @ApiOperation(value = "Create new tenant")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Register was created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PostMapping(produces="application/json", consumes="application/json")
    public ResponseEntity<TenantResponseDTO> create (@RequestBody TenantCreateDTO tenantCreateDTO) {
        Tenant tenant = tenantPersistenceService.save(TenantMapper.createDtoToEntity(tenantCreateDTO));
        return new ResponseEntity<>(TenantMapper.entityToDTO(tenant), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit tenant")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Resquest responses OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity<TenantResponseDTO> update (
            @NonNull @PathVariable(value = "id") String id,
            @RequestBody TenantUpdateDTO tenantUpdateDTO) {
        Tenant tenant = tenantPersistenceService.update(id, TenantMapper.updateDtoToEntity(tenantUpdateDTO));
        return ResponseEntity.ok(TenantMapper.entityToDTO(tenant));
    }
}