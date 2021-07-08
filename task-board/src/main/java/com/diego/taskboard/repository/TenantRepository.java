package com.diego.taskboard.repository;

import com.diego.taskboard.domain.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TenantRepository extends MongoRepository<Tenant, String> {
}
