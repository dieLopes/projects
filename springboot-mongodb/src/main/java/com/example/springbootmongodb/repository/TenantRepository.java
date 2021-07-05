package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.domain.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TenantRepository extends MongoRepository<Tenant, String> {
}
