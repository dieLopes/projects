package com.example.springbootmongodb.api.v1.mapper;

import com.example.springbootmongodb.builder.EmployeeBuilder;
import com.example.springbootmongodb.api.v1.dto.employee.EmployeeCreateDTO;
import com.example.springbootmongodb.api.v1.dto.employee.EmployeeResponseDTO;
import com.example.springbootmongodb.api.v1.dto.employee.EmployeeUpdateDTO;
import com.example.springbootmongodb.builder.TenantBuilder;
import com.example.springbootmongodb.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeMapperTest {

    @Test
    public void whenConvertCreateDTOToEntityThenReturn () {
        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO();
        employeeCreateDTO.setName("Some Name");
        employeeCreateDTO.setAddress("Some Address");
        Employee employee = EmployeeMapper.createDtoToEntity(employeeCreateDTO);
        assertThat(employee.getId()).isNullOrEmpty();
        assertThat(employee.getName()).isEqualTo(employee.getName());
        assertThat(employee.getAddress()).isEqualTo(employee.getAddress());
    }

    @Test
    public void whenConvertUpdateDTOToEntityThenReturn () {
        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setName("Some Name");
        employeeUpdateDTO.setAddress("Some Address");
        Employee employee = EmployeeMapper.updateDtoToEntity(employeeUpdateDTO);
        assertThat(employee.getId()).isNullOrEmpty();
        assertThat(employee.getName()).isEqualTo(employee.getName());
        assertThat(employee.getAddress()).isEqualTo(employee.getAddress());
    }

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        Employee employee = EmployeeBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .tenant(TenantBuilder.of()
                        .id("some-tenant-id")
                        .build())
                .build();
        EmployeeResponseDTO employeeResponseDTO = EmployeeMapper.entityToDTO(employee);
        assertThat(employeeResponseDTO.getId()).isEqualTo(employee.getId());
        assertThat(employeeResponseDTO.getName()).isEqualTo(employee.getName());
        assertThat(employeeResponseDTO.getAddress()).isEqualTo(employee.getAddress());
        assertThat(employeeResponseDTO.getTenant().getId()).isEqualTo("some-tenant-id");
    }

    @Test
    public void whenConvertEntitiesToListDTOThenReturn () {
        List<Employee> employees = List.of(
            EmployeeBuilder.of()
                    .id("some-id")
                    .name("Some Name")
                    .address("Some Address")
                    .tenant(TenantBuilder.of()
                            .id("some-tenant-id-1")
                            .build())
                    .build(),
            EmployeeBuilder.of()
                    .id("another-id")
                    .name("Another Name")
                    .address("Another Address")
                    .tenant(TenantBuilder.of()
                            .id("some-tenant-id-2")
                            .build())
                    .build());
        List<EmployeeResponseDTO> employeeResponseDTOS = EmployeeMapper.entitiesToDTOs(employees);
        assertThat(employeeResponseDTOS).hasSize(2)
            .extracting(EmployeeResponseDTO::getId,
                    EmployeeResponseDTO::getName,
                    EmployeeResponseDTO::getAddress)
            .containsExactlyInAnyOrder(
                tuple("some-id", "Some Name", "Some Address"),
                tuple("another-id", "Another Name", "Another Address")
            );
    }
}
