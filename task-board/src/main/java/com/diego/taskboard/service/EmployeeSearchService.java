package com.diego.taskboard.service;

import com.diego.taskboard.domain.Employee;
import com.diego.taskboard.exception.EmployeeNotFoundException;
import com.diego.taskboard.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeSearchService {

    private final EmployeeRepository employeeRepository;

    public EmployeeSearchService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Employee findById(String id) {
        if (id == null) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
