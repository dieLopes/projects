package com.diego.taskboard.validator.employee;

import com.diego.taskboard.builder.EmployeeBuilder;
import com.diego.taskboard.exception.EmployeeBadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeNameValidatorTest {

    @InjectMocks
    private EmployeeNameValidator employeeNameValidator;

    @Test
    public void whenTenantNameIsNotNullOrEmptyThenPassWithoutException () {
        employeeNameValidator.validate(EmployeeBuilder.of()
                .name("Some Employee")
                .address("Some Address")
                .build());
    }

    @Test(expected = EmployeeBadRequestException.class)
    public void whenTenantNameIsNullThenReturnException () {
        employeeNameValidator.validate(EmployeeBuilder.of()
                .address("Some Address")
                .build());
    }

    @Test(expected = EmployeeBadRequestException.class)
    public void whenTenantNameIsEmptyThenReturnException () {
        employeeNameValidator.validate(EmployeeBuilder.of()
                .name("")
                .address("Some Address")
                .build());
    }
}
