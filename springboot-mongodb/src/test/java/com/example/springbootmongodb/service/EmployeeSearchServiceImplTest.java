package com.example.springbootmongodb.service;

import com.example.springbootmongodb.builder.EmployeeBuilder;
import com.example.springbootmongodb.builder.TenantBuilder;
import com.example.springbootmongodb.domain.Employee;
import com.example.springbootmongodb.exception.EmployeeNotFoundException;
import com.example.springbootmongodb.repository.EmployeeRepository;
import com.example.springbootmongodb.service.impl.EmployeeSearchServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeSearchServiceImplTest {

    @InjectMocks
    private EmployeeSearchServiceImpl employeeSearchService;
    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindEmployeeByIdThenReturnEmployee () throws EmployeeNotFoundException {
        Employee employee = buildEmployee("some-id", "Some Name", "Some Address");
        when(employeeRepository.findById(eq(employee.getId()))).thenReturn(Optional.of(employee));
        assertThat(employeeSearchService.findById(employee.getId())).satisfies(findEmployee -> {
            assertThat(findEmployee.getId()).isEqualTo(employee.getId());
            assertThat(findEmployee.getName()).isEqualTo(employee.getName());
            assertThat(findEmployee.getAddress()).isEqualTo(employee.getAddress());
        });
        verify(employeeRepository).findById(eq(employee.getId()));
    }

    @Test
    public void whenFindEmployeeByIdButNotFoundThenReturnException () {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> employeeSearchService.findById("some-id"))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee not found");
        verify(employeeRepository).findById(anyString());
    }

    @Test
    public void whenFindEmployeeByIdButIdIsNullThenReturnException () {
        assertThatThrownBy(() -> employeeSearchService.findById(null))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Employee not found");
        verify(employeeRepository, never()).findById(anyString());
    }

    @Test
    public void whenFindAllEmployeesThenReturnEmployees () {
        List<Employee> employees = List.of(
                buildEmployee("some-id", "Some Name", "Some Address"),
                buildEmployee("another-id", "Another Name", "Another Address"));
        when(employeeRepository.findAll()).thenReturn(employees);
        assertThat(employeeSearchService.findAll()).hasSize(2)
                .extracting(Employee::getId,
                        Employee::getName,
                        Employee::getAddress)
                .containsExactlyInAnyOrder(
                        tuple("some-id", "Some Name", "Some Address"),
                        tuple("another-id", "Another Name", "Another Address")
                );
        verify(employeeRepository).findAll();
    }

    private Employee buildEmployee (String id, String name, String address) {
        return EmployeeBuilder.of()
                .id(id)
                .name(name)
                .address(address)
                .tenant(TenantBuilder.of()
                        .id("some-tenant-id")
                        .build())
                .build();
    }
}