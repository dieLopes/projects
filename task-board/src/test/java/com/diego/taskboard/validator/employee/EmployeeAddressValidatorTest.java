package com.diego.taskboard.validator.employee;

import com.diego.taskboard.builder.EmployeeBuilder;
import com.diego.taskboard.exception.EmployeeBadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeAddressValidatorTest {

    @InjectMocks
    private EmployeeAddressValidator employeeAddressValidator;

    @Test
    public void whenTenantNameIsNotNullOrEmptyThenPassWithoutException () {
        employeeAddressValidator.validate(EmployeeBuilder.of()
                .name("Some Name")
                .address("Some Address")
                .build());
    }

    @Test(expected = EmployeeBadRequestException.class)
    public void whenTenantNameIsNullThenReturnException () {
        employeeAddressValidator.validate(EmployeeBuilder.of()
                .name("Some Name")
                .build());
    }

    @Test(expected = EmployeeBadRequestException.class)
    public void whenTenantNameIsEmptyThenReturnException () {
        employeeAddressValidator.validate(EmployeeBuilder.of()
                .name("Some Name")
                .address("")
                .build());
    }
}
