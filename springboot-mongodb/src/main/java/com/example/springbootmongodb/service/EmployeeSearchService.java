package com.example.springbootmongodb.service;

import com.example.springbootmongodb.domain.Employee;

import java.util.List;

public interface EmployeeSearchService {

    Employee findById(String id);

    List<Employee> findAll();
}
