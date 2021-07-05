package com.example.springbootmongodb.api.v1.controller;

import com.example.springbootmongodb.api.v1.dto.employee.EmployeeCreateDTO;
import com.example.springbootmongodb.api.v1.dto.employee.EmployeeResponseDTO;
import com.example.springbootmongodb.api.v1.dto.employee.EmployeeResponseListDTO;
import com.example.springbootmongodb.api.v1.dto.employee.EmployeeUpdateDTO;
import com.example.springbootmongodb.api.v1.mapper.EmployeeMapper;
import com.example.springbootmongodb.domain.Employee;
import com.example.springbootmongodb.service.EmployeePersistenceService;
import com.example.springbootmongodb.service.EmployeeSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById (@NonNull @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(EmployeeMapper.entityToDTO(employeeSearchService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<EmployeeResponseListDTO> getAll () {
        return ResponseEntity.ok(new EmployeeResponseListDTO(EmployeeMapper.entitiesToDTOs(employeeSearchService.findAll())));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create (@RequestBody EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = employeePersistenceService.save(EmployeeMapper.createDtoToEntity(employeeCreateDTO));
        return new ResponseEntity<>(EmployeeMapper.entityToDTO(employee), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeResponseDTO> update (
            @NonNull @PathVariable(value = "id") String id,
            @RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        Employee employee = employeePersistenceService.update(id, EmployeeMapper.updateDtoToEntity(employeeUpdateDTO));
        return ResponseEntity.ok(EmployeeMapper.entityToDTO(employee));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete (@PathVariable(value = "id") String id) {
        employeePersistenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}