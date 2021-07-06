package com.example.springbootmongodb.service;

import com.example.springbootmongodb.domain.Tenant;
import com.example.springbootmongodb.exception.TenantBadRequestException;
import com.example.springbootmongodb.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TenantPersistenceService {

    private final TenantRepository tenantRepository;
    private final TenantSearchService tenantSearchService;

    public TenantPersistenceService(TenantRepository employeeRepository,
                                    TenantSearchService employeeSearchService) {
        this.tenantRepository = employeeRepository;
        this.tenantSearchService = employeeSearchService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Tenant save(Tenant tenant) {
        if (tenant.getId() != null) {
            throw new TenantBadRequestException("Tenant id must be null");
        }
        validateTenant(tenant);
        tenant.setId(UUID.randomUUID().toString());
        return tenantRepository.save(tenant);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Tenant update(String id, Tenant tenant) {
        tenantSearchService.findById(id);
        validateTenant(tenant);
        tenant.setId(id);
        return tenantRepository.save(tenant);
    }

    private void validateTenant (Tenant tenant) {
        if (tenant.getName() == null || tenant.getName().isEmpty()) {
            throw new TenantBadRequestException("Name is a mandatory field");
        }
    }
}