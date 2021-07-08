package com.diego.taskboard.api.v1.mapper;

import com.diego.taskboard.api.v1.dto.employee.EmployeeCreateDTO;
import com.diego.taskboard.api.v1.dto.employee.EmployeeResponseDTO;
import com.diego.taskboard.api.v1.dto.employee.EmployeeUpdateDTO;
import com.diego.taskboard.builder.TenantBuilder;
import com.diego.taskboard.domain.Employee;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Employee createDtoToEntity(EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = mapper.map(employeeCreateDTO, Employee.class);
        employee.setTenant(TenantBuilder.of().id(employeeCreateDTO.getTenantId()).build());
        return employee;
    }

    public static Employee updateDtoToEntity (EmployeeUpdateDTO employeeUpdateDTO) {
        return mapper.map(employeeUpdateDTO, Employee.class);
    }

    public static EmployeeResponseDTO entityToDTO(Employee employee) {
        EmployeeResponseDTO employeeResponseDTO = mapper.map(employee, EmployeeResponseDTO.class);
        employeeResponseDTO.setTenant(TenantMapper.entityToDTO(employee.getTenant()));
        return employeeResponseDTO;
    }

    public static List<EmployeeResponseDTO> entitiesToDTOs (List<Employee> employees) {
        return employees
            .stream()
            .map(EmployeeMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
