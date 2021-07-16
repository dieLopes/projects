package com.diego.taskboard.validator.employee;

import com.diego.taskboard.domain.Employee;
import com.diego.taskboard.exception.EmployeeBadRequestException;
import com.diego.taskboard.validator.IValidator;
import org.springframework.stereotype.Service;

@Service
public class EmployeeNameValidator implements IValidator<Employee> {

    @Override
    public void validate(Employee employee) {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new EmployeeBadRequestException("Name is a mandatory field");
        }
    }
}
