package com.example.springbootmongodb.service;

import com.example.springbootmongodb.domain.Tenant;
import com.example.springbootmongodb.exception.TenantNotFoundException;
import com.example.springbootmongodb.repository.TenantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TenantSearchService {

    private final TenantRepository tenantRepository;

    public TenantSearchService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Tenant findById(String id) {
        if (id == null) {
            throw new TenantNotFoundException("Tenant not found");
        }
        return tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }
}
