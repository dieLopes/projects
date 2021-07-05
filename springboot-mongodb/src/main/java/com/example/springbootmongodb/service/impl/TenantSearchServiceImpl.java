package com.example.springbootmongodb.service.impl;

import com.example.springbootmongodb.domain.Tenant;
import com.example.springbootmongodb.exception.TenantNotFoundException;
import com.example.springbootmongodb.repository.TenantRepository;
import com.example.springbootmongodb.service.TenantSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TenantSearchServiceImpl implements TenantSearchService {

    private TenantRepository tenantRepository;

    public TenantSearchServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Tenant findById(String id) {
        if (id == null) {
            throw new TenantNotFoundException("Tenant not found");
        }
        return tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }
}
