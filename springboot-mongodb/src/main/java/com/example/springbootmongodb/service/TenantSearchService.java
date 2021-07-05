package com.example.springbootmongodb.service;

import com.example.springbootmongodb.domain.Tenant;

import java.util.List;

public interface TenantSearchService {

    Tenant findById(String id);

    List<Tenant> findAll();
}
