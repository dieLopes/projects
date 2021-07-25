package com.diego.taskboard.api.v1.mapper;

import com.diego.taskboard.api.v1.dto.tenant.TenantCreateDTO;
import com.diego.taskboard.api.v1.dto.tenant.TenantResponseDTO;
import com.diego.taskboard.api.v1.dto.tenant.TenantUpdateDTO;
import com.diego.taskboard.domain.Tenant;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class TenantMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Tenant createDtoToEntity(TenantCreateDTO tenantCreateDTO) {
        return mapper.map(tenantCreateDTO, Tenant.class);
    }

    public static Tenant updateDtoToEntity (TenantUpdateDTO tenantUpdateDTO) {
        return mapper.map(tenantUpdateDTO, Tenant.class);
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
