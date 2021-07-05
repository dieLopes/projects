package com.example.springbootmongodb.service.impl;

import com.example.springbootmongodb.domain.Employee;
import com.example.springbootmongodb.domain.Tenant;
import com.example.springbootmongodb.exception.EmployeeBadRequestException;
import com.example.springbootmongodb.exception.TenantNotFoundException;
import com.example.springbootmongodb.repository.EmployeeRepository;
import com.example.springbootmongodb.service.EmployeePersistenceService;
import com.example.springbootmongodb.service.EmployeeSearchService;
import com.example.springbootmongodb.service.TenantSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EmployeePersistenceServiceImpl implements EmployeePersistenceService {

    private EmployeeRepository employeeRepository;
    private EmployeeSearchService employeeSearchService;
    private TenantSearchService tenantSearchService;

    public EmployeePersistenceServiceImpl(EmployeeRepository employeeRepository,
                                          EmployeeSearchService employeeSearchService,
                                          TenantSearchService tenantSearchService) {
        this.employeeRepository = employeeRepository;
        this.employeeSearchService = employeeSearchService;
        this.tenantSearchService = tenantSearchService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Employee save(Employee employee) {
        Tenant tenant;
        try {
            tenant = tenantSearchService.findById(employee.getId());
        } catch (TenantNotFoundException e) {
            throw new EmployeeBadRequestException("Tenant not found");
        }
        employee.setId(UUID.randomUUID().toString());
        employee.setTenant(tenant);
        validateEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Employee update(String id, Employee employee) {
        Employee oldEmployee =employeeSearchService.findById(id);
        validateEmployee(employee);
        employee.setId(id);
        employee.setTenant(oldEmployee.getTenant());
        return employeeRepository.save(employee);
    }

    @Override
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