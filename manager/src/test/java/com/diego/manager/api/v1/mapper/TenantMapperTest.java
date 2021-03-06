package com.diego.manager.api.v1.mapper;

import com.diego.manager.api.v1.dto.tenant.TenantCreateDTO;
import com.diego.manager.api.v1.dto.tenant.TenantResponseDTO;
import com.diego.manager.api.v1.dto.tenant.TenantUpdateDTO;
import com.diego.manager.builder.TenantBuilder;
import com.diego.manager.domain.Tenant;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class TenantMapperTest {

    @Test
    public void whenConvertCreateDTOToEntityThenReturn () {
        TenantCreateDTO tenantCreateDTO = new TenantCreateDTO();
        tenantCreateDTO.setName("Some Name");
        Tenant tenant = TenantMapper.createDtoToEntity(tenantCreateDTO);
        assertThat(tenant.getId()).isNullOrEmpty();
        assertThat(tenant.getName()).isEqualTo(tenant.getName());
    }

    @Test
    public void whenConvertUpdateDTOToEntityThenReturn () {
        TenantUpdateDTO tenantUpdateDTO = new TenantUpdateDTO();
        tenantUpdateDTO.setName("Some Name");
        Tenant tenant = TenantMapper.updateDtoToEntity(tenantUpdateDTO);
        assertThat(tenant.getId()).isNullOrEmpty();
        assertThat(tenant.getName()).isEqualTo(tenant.getName());
    }

    @Test
    public void whenConvertEntityToDTOThenReturn () {
        Tenant tenant = TenantBuilder.of()
                .id("some-id")
                .name("Some Name")
                .build();
        TenantResponseDTO tenantResponseDTO = TenantMapper.entityToDTO(tenant);
        assertThat(tenantResponseDTO.getId()).isEqualTo(tenant.getId());
        assertThat(tenantResponseDTO.getName()).isEqualTo(tenant.getName());
    }

    @Test
    public void whenConvertEntitiesToListDTOThenReturn () {
        List<Tenant> tenants = List.of(
                TenantBuilder.of()
                        .id("some-id")
                        .name("Some Name")
                        .build(),
                TenantBuilder.of()
                        .id("another-id")
                        .name("Another Name")
                        .build());
        List<TenantResponseDTO> tenantResponseDTOS = TenantMapper.entitiesToDTOs(tenants);
        assertThat(tenantResponseDTOS).hasSize(2)
                .extracting(TenantResponseDTO::getId,
                        TenantResponseDTO::getName)
                .containsExactlyInAnyOrder(
                        tuple("some-id", "Some Name"),
                        tuple("another-id", "Another Name")
                );
    }
}
