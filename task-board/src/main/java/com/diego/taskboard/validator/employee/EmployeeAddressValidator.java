package com.diego.taskboard.validator.employee;

import com.diego.taskboard.domain.Employee;
import com.diego.taskboard.exception.EmployeeBadRequestException;
import com.diego.taskboard.validator.IValidator;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAddressValidator implements IValidator<Employee> {

    @Override
    public void validate(Employee employee) {
        if (employee.getAddress() == null || employee.getAddress().isEmpty()) {
            throw new EmployeeBadRequestException("Address is a mandatory field");
        }
    }
}
