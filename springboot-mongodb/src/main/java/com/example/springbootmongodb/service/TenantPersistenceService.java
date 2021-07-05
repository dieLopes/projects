package com.example.springbootmongodb.service;

import com.example.springbootmongodb.domain.Tenant;

public interface TenantPersistenceService {

    Tenant save (Tenant tenant);

    Tenant update (String id, Tenant tenant);
}
