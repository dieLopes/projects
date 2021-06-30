package com.example.springbootmongodb.controller;

import com.example.springbootmongodb.controller.dto.employee.EmployeeCreateDTO;
import com.example.springbootmongodb.controller.dto.employee.EmployeeResponseDTO;
import com.example.springbootmongodb.controller.dto.employee.EmployeeResponseListDTO;
import com.example.springbootmongodb.controller.dto.employee.EmployeeUpdateDTO;
import com.example.springbootmongodb.domain.Employee;
import com.example.springbootmongodb.mapper.EmployeeMapper;
import com.example.springbootmongodb.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById (@NonNull @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(EmployeeMapper.entityToDTO(employeeService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<EmployeeResponseListDTO> getAll () {
        return ResponseEntity.ok(new EmployeeResponseListDTO(EmployeeMapper.entitiesToDTOs(employeeService.findAll())));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create (@RequestBody EmployeeCreateDTO employeeCreateDTO) {
        Employee employee = employeeService.save(EmployeeMapper.createDtoToEntity(employeeCreateDTO));
        return new ResponseEntity<>(EmployeeMapper.entityToDTO(employee), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeResponseDTO> update (
            @NonNull @PathVariable(value = "id") String id,
            @RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        Employee employee = employeeService.update(id, EmployeeMapper.updateDtoToEntity(employeeUpdateDTO));
        return ResponseEntity.ok(EmployeeMapper.entityToDTO(employee));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete (@PathVariable(value = "id") String id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}