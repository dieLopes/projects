package com.example.springbootmongodb.service;

import com.example.springbootmongodb.domain.Employee;

import java.util.List;

public interface EmployeeService {

    Employee findById(String id);

    List<Employee> findAll();

    Employee save (Employee employee);

    Employee update (String id, Employee employee);

    void delete (String id);


}
