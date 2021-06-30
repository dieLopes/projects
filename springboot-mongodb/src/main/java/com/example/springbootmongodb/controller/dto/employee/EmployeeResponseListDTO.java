package com.example.springbootmongodb.controller.dto.employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeResponseListDTO {

    private List<EmployeeResponseDTO> employees = new ArrayList<>();

    public EmployeeResponseListDTO() { }

    public EmployeeResponseListDTO(List<EmployeeResponseDTO> employees) {
        this.employees = employees;
    }

    public List<EmployeeResponseDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponseDTO> employees) {
        this.employees = employees;
    }
}
