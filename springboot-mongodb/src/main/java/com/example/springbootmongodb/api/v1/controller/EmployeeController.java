package com.example.springbootmongodb.api.v1.controller;

import com.example.springbootmongodb.api.v1.dto.employee.EmployeeCreateDTO;
import com.example.springbootmongodb.api.v1.dto.employee.EmployeeResponseDTO;
import com.example.springbootmongodb.api.v1.dto.employee.EmployeeResponseListDTO;
import com.example.springbootmongodb.api.v1.dto.employee.EmployeeUpdateDTO;
import com.example.springbootmongodb.api.v1.mapper.EmployeeMapper;
import com.example.springbootmongodb.domain.Employee;
import com.example.springbootmongodb.service.EmployeePersistenceService;
import com.example.springbootmongodb.service.EmployeeSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Employee Service")
@RestController
@RequestMapping(ApiPath.API_PATH + "employees")
public class EmployeeController {

    private EmployeePersistenceService employeePersistenceService;
    private EmployeeSearchService employeeSearchService;

    public EmployeeController(EmployeePersistenceService employeePersistenceService,
                              EmployeeSearchService employeeSearchService) {
        this.employeePersistenceService = employeePersistenceService;
        this.employeeSearchService = employeeSearchService;
    }

    @ApiOperation(value = "Return employee by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resquest responses OK"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(value = "/{id}", produces="application/json")
    public ResponseEntity<EmployeeResponseDTO> getById (@NonNull @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(EmployeeMapper.entityToDTO(employeeSearchService.findById(id)));
    }

    @ApiOperation(value = "Return all employees")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Resquest responses OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @GetMapping(produces="application/json")
    public ResponseEntity<EmployeeResponseListDTO> getAll () {
        return ResponseEntity.ok(new EmployeeResponseListDTO(EmployeeMapper.entitiesToDTOs(employeeSearchService.findAll())));
    }

    @ApiOperation(value = "Create new employee")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Register was created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PostMapping(produces="application/json", consumes="application/json")
    public ResponseEntity<EmployeeResponseDTO> create (@RequestBody EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = employeePersistenceService.save(EmployeeMapper.createDtoToEntity(employeeCreateDTO));
        return new ResponseEntity<>(EmployeeMapper.entityToDTO(employee), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit employee")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Resquest responses OK"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    public ResponseEntity<EmployeeResponseDTO> update (
            @NonNull @PathVariable(value = "id") String id,
            @RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        Employee employee = employeePersistenceService.update(id, EmployeeMapper.updateDtoToEntity(employeeUpdateDTO));
        return ResponseEntity.ok(EmployeeMapper.entityToDTO(employee));
    }

    @ApiOperation(value = "Remove employee")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Resquest responses OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Server error"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete (@PathVariable(value = "id") String id) {
        employeePersistenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}