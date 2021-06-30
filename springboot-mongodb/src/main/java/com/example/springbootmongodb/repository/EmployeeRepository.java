package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
