package com.diego.taskboard.service;

import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.exception.TenantBadRequestException;
import com.diego.taskboard.repository.TenantRepository;
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