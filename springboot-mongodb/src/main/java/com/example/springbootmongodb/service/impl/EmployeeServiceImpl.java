package com.example.springbootmongodb.service.impl;

import com.example.springbootmongodb.domain.Employee;
import com.example.springbootmongodb.exception.EmployeeBadRequestException;
import com.example.springbootmongodb.exception.EmployeeNotFoundException;
import com.example.springbootmongodb.repository.EmployeeRepository;
import com.example.springbootmongodb.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee save(Employee employee) {
        validateEmployee(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(String id, Employee employee) {
        findById(id);
        validateEmployee(employee);
        employee.setId(id);
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(String id) {
        Employee employee = findById(id);
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

    private Employee findBoss (String bossId) {
       return employeeRepository.findById(bossId)
                .orElseThrow(() -> new EmployeeNotFoundException("Boss not found"));
    }
}