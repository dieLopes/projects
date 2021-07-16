package com.diego.taskboard.service;

import com.diego.taskboard.builder.EmployeeBuilder;
import com.diego.taskboard.builder.TenantBuilder;
import com.diego.taskboard.domain.Employee;
import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.exception.EmployeeNotFoundException;
import com.diego.taskboard.repository.EmployeeRepository;
import com.diego.taskboard.validator.IValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeePersistenceServiceTest {

    @InjectMocks
    private EmployeePersistenceService employeePersistenceService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeSearchService employeeSearchService;
    @Mock
    private TenantSearchService tenantSearchService;
    @Mock
    private List<IValidator<Employee>> validators;

    @Test
    public void whenCreateEmployeeThenSaveEmployee () {
        Employee employee = buildEmployee();
        employee.setId(null);
        when(tenantSearchService.findById(eq("some-tenant-id"))).thenReturn(new Tenant());
        when(employeeRepository.save(eq(employee))).thenReturn(employee);
        employeePersistenceService.save(employee);
        verify(employeeRepository).save(any(Employee.class));
        verify(tenantSearchService).findById(eq("some-tenant-id"));
        verify(validators).forEach(any(Consumer.class));
    }

    @Test
    public void whenUpdateEmployeeThenSaveEmployee () {
        Employee employee = buildEmployee();
        when(employeeSearchService.findById(eq(employee.getId()))).thenReturn(employee);
        when(employeeRepository.save(eq(employee))).thenReturn(employee);
        employeePersistenceService.update(employee.getId(), employee);
        verify(employeeRepository).save(any(Employee.class));
        verify(employeeSearchService).findById(eq(employee.getId()));
        verify(validators).forEach(any(Consumer.class));
    }

    @Test
    public void whenUpdateEmployeeWithoutIdThenReturnException () {
        Employee employee = buildEmployee();
        employee.setId(null);
        when(employeeSearchService.findById(eq(null)))
                .thenThrow(new EmployeeNotFoundException("Employee not found"));
        assertThatThrownBy(() ->  employeePersistenceService.update(employee.getId(), employee))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee not found");
        verify(employeeSearchService).findById(eq(employee.getId()));
        verify(employeeRepository, never()).save(any(Employee.class));
        verifyNoInteractions(validators);
    }

    @Test
    public void whenUpdateEmployeeNotFoundThenReturnException () {
        Employee employee = buildEmployee();
        when(employeeSearchService.findById(eq(employee.getId())))
                .thenThrow(new EmployeeNotFoundException("Employee not found"));
        assertThatThrownBy(() -> employeePersistenceService.update(employee.getId(), employee))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee not found");
        verify(employeeSearchService).findById(eq(employee.getId()));
        verify(employeeRepository, never()).save(any(Employee.class));
        verifyNoInteractions(validators);
    }

    @Test
    public void whenDeleteEmployeeThenRemoveFromDatabase () throws EmployeeNotFoundException {
        Employee employee = buildEmployee();
        when(employeeSearchService.findById(eq(employee.getId()))).thenReturn(employee);
        employeePersistenceService.delete(employee.getId());
        verify(employeeSearchService).findById(eq(employee.getId()));
        verify(employeeRepository).delete(eq(employee));
    }

    @Test
    public void whenDeleteEmployeeButNotFoundThenReturnException () throws EmployeeNotFoundException {
        Employee employee = buildEmployee();
        when(employeeSearchService.findById(eq(employee.getId())))
                .thenThrow(new EmployeeNotFoundException("Employee not found"));
        assertThatThrownBy(() ->  employeePersistenceService.delete(employee.getId()))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee not found");
        verify(employeeSearchService).findById(eq(employee.getId()));
        verify(employeeRepository, never()).delete(eq(employee));
    }

    private Employee buildEmployee () {
        return EmployeeBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .tenant(TenantBuilder.of()
                        .id("some-tenant-id")
                        .build())
                .build();
    }
}
