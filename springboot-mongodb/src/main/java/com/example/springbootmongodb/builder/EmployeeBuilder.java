package com.example.springbootmongodb.builder;

import com.example.springbootmongodb.domain.Employee;
import com.example.springbootmongodb.domain.Tenant;

public final class EmployeeBuilder {

    private Employee employee;

    private EmployeeBuilder() {
        employee = new Employee();
    }

    public static EmployeeBuilder of () {
        return new EmployeeBuilder();
    }

    public EmployeeBuilder id (String id) {
        employee.setId(id);
        return this;
    }

    public EmployeeBuilder name (String name) {
        employee.setName(name);
        return this;
    }

    public EmployeeBuilder address (String address) {
        employee.setAddress(address);
        return this;
    }

    public EmployeeBuilder tenant (Tenant tenant) {
        employee.setTenant(tenant);
        return this;
    }

    public Employee build () {
        return employee;
    }
}