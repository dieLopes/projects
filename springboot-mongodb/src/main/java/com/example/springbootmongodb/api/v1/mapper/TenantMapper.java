package com.example.springbootmongodb.api.v1.mapper;

import com.example.springbootmongodb.api.v1.dto.tenant.TenantCreateDTO;
import com.example.springbootmongodb.api.v1.dto.tenant.TenantResponseDTO;
import com.example.springbootmongodb.api.v1.dto.tenant.TenantUpdateDTO;
import com.example.springbootmongodb.domain.Tenant;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class TenantMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Tenant createDtoToEntity(TenantCreateDTO tenantCreateDTO) {
        return mapper.map(tenantCreateDTO, Tenant.class);
    }

    public static Tenant updateDtoToEntity (TenantUpdateDTO employeeUpdateDTO) {
        return mapper.map(employeeUpdateDTO, Tenant.class);
    }

    public static TenantResponseDTO entityToDTO(Tenant tenant) {
        return mapper.map(tenant, TenantResponseDTO.class);
    }

    public static List<TenantResponseDTO> entitiesToDTOs (List<Tenant> tenants) {
        return tenants
                .stream()
                .map(TenantMapper::entityToDTO)
                .collect(Collectors.toList());
    }
}
