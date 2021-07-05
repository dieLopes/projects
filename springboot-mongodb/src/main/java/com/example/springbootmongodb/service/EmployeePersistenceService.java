package com.example.springbootmongodb.service;

import com.example.springbootmongodb.domain.Employee;

public interface EmployeePersistenceService {

    Employee save (Employee employee);

    Employee update (String id, Employee employee);

    void delete (String id);
}