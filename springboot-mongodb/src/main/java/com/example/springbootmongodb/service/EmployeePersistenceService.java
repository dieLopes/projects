package com.example.springbootmongodb.service;

import com.example.springbootmongodb.domain.Employee;
import com.example.springbootmongodb.domain.Tenant;
import com.example.springbootmongodb.exception.EmployeeBadRequestException;
import com.example.springbootmongodb.exception.TenantNotFoundException;
import com.example.springbootmongodb.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EmployeePersistenceService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeSearchService employeeSearchService;
    private final TenantSearchService tenantSearchService;

    public EmployeePersistenceService(EmployeeRepository employeeRepository,
                                      EmployeeSearchService employeeSearchService,
                                      TenantSearchService tenantSearchService) {
        this.employeeRepository = employeeRepository;
        this.employeeSearchService = employeeSearchService;
        this.tenantSearchService = tenantSearchService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Employee save(Employee employee) {
        if (employee.getId() != null) {
            throw new EmployeeBadRequestException("Employee id must be null");
        }
        Tenant tenant;
        try {
            tenant = tenantSearchService.findById(employee.getTenant().getId());
        } catch (TenantNotFoundException e) {
            throw new EmployeeBadRequestException("Tenant not found");
        }
        employee.setId(UUID.randomUUID().toString());
        employee.setTenant(tenant);
        validateEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Employee update(String id, Employee employee) {
        Employee oldEmployee =employeeSearchService.findById(id);
        validateEmployee(employee);
        employee.setId(id);
        employee.setTenant(oldEmployee.getTenant());
        return employeeRepository.save(employee);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String id) {
        Employee employee = employeeSearchService.findById(id);
        employeeRepository.delete(employee);
    }

    private void validateEmployee (Employee employee) {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new EmployeeBadRequestException("Name is a mandatory field");
        }
        if (employee.getAddress() == null || employee.getAddress().isEmpty()) {
            throw new EmployeeBadRequestException("Address is a mandatory field");
        }
    }
}