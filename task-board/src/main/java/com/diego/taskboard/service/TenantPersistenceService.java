package com.diego.taskboard.service;

import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.repository.TenantRepository;
import com.diego.taskboard.validator.IValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TenantPersistenceService {

    private final TenantRepository tenantRepository;
    private final TenantSearchService tenantSearchService;
    private final List<IValidator<Tenant>> validators;

    public TenantPersistenceService(TenantRepository tenantRepository,
                                    TenantSearchService tenantSearchService,
                                    List<IValidator<Tenant>> validators) {
        this.tenantRepository = tenantRepository;
        this.tenantSearchService = tenantSearchService;
        this.validators = validators;
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
        validators.forEach(val -> val.validate(tenant));
    }
}