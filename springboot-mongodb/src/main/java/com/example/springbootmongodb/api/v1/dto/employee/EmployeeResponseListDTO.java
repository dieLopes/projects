package com.example.springbootmongodb.api.v1.dto.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "EmployeeResponseList")
public class EmployeeResponseListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of employees")
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
