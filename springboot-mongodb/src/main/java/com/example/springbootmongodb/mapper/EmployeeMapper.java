package com.example.springbootmongodb.mapper;

import com.example.springbootmongodb.controller.dto.employee.EmployeeCreateDTO;
import com.example.springbootmongodb.controller.dto.employee.EmployeeResponseDTO;
import com.example.springbootmongodb.controller.dto.employee.EmployeeUpdateDTO;
import com.example.springbootmongodb.domain.Employee;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Employee createDtoToEntity(EmployeeCreateDTO employeeCreateDTO) {
        return mapper.map(employeeCreateDTO, Employee.class);
    }

    public static Employee updateDtoToEntity (EmployeeUpdateDTO employeeUpdateDTO) {
        return mapper.map(employeeUpdateDTO, Employee.class);
    }

    public static EmployeeResponseDTO entityToDTO(Employee employee) {
        return mapper.map(employee, EmployeeResponseDTO.class);
    }

    public static List<EmployeeResponseDTO> entitiesToDTOs (List<Employee> employees) {
        return employees
            .stream()
            .map(EmployeeMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
