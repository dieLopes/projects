package com.diego.taskboard.service;

import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.repository.TenantRepository;
import com.diego.taskboard.validator.IValidator;
import com.diego.taskboard.validator.tenant.TenantNameValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TenantPersistenceService {

    private final TenantRepository tenantRepository;
    private final TenantSearchService tenantSearchService;
    private static final List<IValidator<Tenant>> validations = new ArrayList<>();
    static {
        validations.add(new TenantNameValidator());
    }

    public TenantPersistenceService(TenantRepository tenantRepository,
                                    TenantSearchService tenantSearchService) {
        this.tenantRepository = tenantRepository;
        this.tenantSearchService = tenantSearchService;
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
        validations.forEach(val -> val.validate(tenant));
    }
}