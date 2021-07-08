package com.diego.taskboard.service;

import com.diego.taskboard.builder.EmployeeBuilder;
import com.diego.taskboard.builder.TenantBuilder;
import com.diego.taskboard.domain.Employee;
import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.exception.EmployeeBadRequestException;
import com.diego.taskboard.exception.EmployeeNotFoundException;
import com.diego.taskboard.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

    @Test
    public void whenCreateEmployeeThenSaveEmployee () {
        Employee employee = buildEmployee();
        employee.setId(null);
        when(tenantSearchService.findById(eq("some-tenant-id"))).thenReturn(new Tenant());
        when(employeeRepository.save(eq(employee))).thenReturn(employee);
        employeePersistenceService.save(employee);
        verify(employeeRepository).save(any(Employee.class));
        verify(tenantSearchService).findById(eq("some-tenant-id"));
    }

    @Test
    public void whenCreateEmployeeWithIdThenReturnException () {
        Employee employee = buildEmployee();
        assertThatThrownBy(() -> employeePersistenceService.save(employee))
                .isInstanceOf(EmployeeBadRequestException.class)
                .hasMessage("Employee id must be null");
        verify(employeeRepository, never()).save(any(Employee.class));
        verify(tenantSearchService, never()).findById(anyString());
    }

    @Test
    public void whenCreateEmployeeWithoutNameThenReturnException () {
        Employee employee = buildEmployee();
        employee.setName(null);
        employee.setId(null);
        assertThatThrownBy(() -> employeePersistenceService.save(employee))
                .isInstanceOf(EmployeeBadRequestException.class)
                .hasMessage("Name is a mandatory field");
        verify(employeeRepository, never()).save(any(Employee.class));
        verify(tenantSearchService).findById(anyString());
    }

    @Test
    public void whenCreateEmployeeWithoutAddressThenReturnException () {
        Employee employee = buildEmployee();
        employee.setAddress(null);
        employee.setId(null);
        assertThatThrownBy(() -> employeePersistenceService.save(employee))
                .isInstanceOf(EmployeeBadRequestException.class)
                .hasMessage("Address is a mandatory field");
        verify(employeeRepository, never()).save(any(Employee.class));
        verify(tenantSearchService).findById(anyString());
    }

    @Test
    public void whenUpdateEmployeeThenSaveEmployee () {
        Employee employee = buildEmployee();
        when(employeeSearchService.findById(eq(employee.getId()))).thenReturn(employee);
        when(employeeRepository.save(eq(employee))).thenReturn(employee);
        employeePersistenceService.update(employee.getId(), employee);
        verify(employeeRepository).save(any(Employee.class));
        verify(employeeSearchService).findById(eq(employee.getId()));
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
    }

    @Test
    public void whenUpdateEmployeeWithoutNameThenReturnException () {
        Employee employee = buildEmployee();
        employee.setName(null);
        assertThatThrownBy(() -> employeePersistenceService.update(employee.getId(), employee))
                .isInstanceOf(EmployeeBadRequestException.class)
                .hasMessage("Name is a mandatory field");
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void whenUpdateEmployeeWithoutAddressThenReturnException () {
        Employee employee = buildEmployee();
        employee.setAddress(null);
        assertThatThrownBy(() -> employeePersistenceService.update(employee.getId(), employee))
                .isInstanceOf(EmployeeBadRequestException.class)
                .hasMessage("Address is a mandatory field");
        verify(employeeRepository, never()).save(any(Employee.class));
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
