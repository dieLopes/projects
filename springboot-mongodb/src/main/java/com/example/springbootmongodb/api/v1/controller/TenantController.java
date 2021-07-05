package com.example.springbootmongodb.api.v1.controller;

import com.example.springbootmongodb.api.v1.dto.tenant.TenantCreateDTO;
import com.example.springbootmongodb.api.v1.dto.tenant.TenantResponseDTO;
import com.example.springbootmongodb.api.v1.dto.tenant.TenantResponseListDTO;
import com.example.springbootmongodb.api.v1.dto.tenant.TenantUpdateDTO;
import com.example.springbootmongodb.api.v1.mapper.TenantMapper;
import com.example.springbootmongodb.domain.Tenant;
import com.example.springbootmongodb.service.TenantPersistenceService;
import com.example.springbootmongodb.service.TenantSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<TenantResponseDTO> getById (@NonNull @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(TenantMapper.entityToDTO(tenantSearchService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<TenantResponseListDTO> getAll () {
        return ResponseEntity.ok(new TenantResponseListDTO(TenantMapper.entitiesToDTOs(tenantSearchService.findAll())));
    }

    @PostMapping
    public ResponseEntity<TenantResponseDTO> create (@RequestBody TenantCreateDTO tenantCreateDTO) {
        Tenant tenant = tenantPersistenceService.save(TenantMapper.createDtoToEntity(tenantCreateDTO));
        return new ResponseEntity<>(TenantMapper.entityToDTO(tenant), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TenantResponseDTO> update (
            @NonNull @PathVariable(value = "id") String id,
            @RequestBody TenantUpdateDTO tenantUpdateDTO) {
        Tenant tenant = tenantPersistenceService.update(id, TenantMapper.updateDtoToEntity(tenantUpdateDTO));
        return ResponseEntity.ok(TenantMapper.entityToDTO(tenant));
    }
}