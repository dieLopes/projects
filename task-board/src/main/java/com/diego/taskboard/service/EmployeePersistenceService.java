package com.diego.taskboard.service;

import com.diego.taskboard.domain.Employee;
import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.exception.EmployeeBadRequestException;
import com.diego.taskboard.exception.TenantNotFoundException;
import com.diego.taskboard.repository.EmployeeRepository;
import com.diego.taskboard.validator.IValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeePersistenceService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeSearchService employeeSearchService;
    private final TenantSearchService tenantSearchService;
    private final List<IValidator<Employee>> validators;

    public EmployeePersistenceService(EmployeeRepository employeeRepository,
                                      EmployeeSearchService employeeSearchService,
                                      TenantSearchService tenantSearchService,
                                      List<IValidator<Employee>> validators) {
        this.employeeRepository = employeeRepository;
        this.employeeSearchService = employeeSearchService;
        this.tenantSearchService = tenantSearchService;
        this.validators = validators;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Employee save(Employee employee) {
        Tenant tenant = tenantSearchService.findById(employee.getTenant().getId());
        employee.setId(UUID.randomUUID().toString());
        employee.setTenant(tenant);
        validateEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Employee update(String id, Employee employee) {
        Employee oldEmployee = employeeSearchService.findById(id);
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
        validators.forEach(val -> val.validate(employee));
    }
}