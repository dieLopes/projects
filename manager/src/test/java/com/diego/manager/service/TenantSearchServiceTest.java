package com.diego.manager.service;

import com.diego.manager.builder.TenantBuilder;
import com.diego.manager.domain.Tenant;
import com.diego.manager.exception.NotFoundException;
import com.diego.manager.repository.TenantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TenantSearchServiceTest {

    @InjectMocks
    private TenantSearchService tenantSearchService;
    @Mock
    private TenantRepository tenantRepository;

    @Test
    public void whenFindTenantByIdThenReturnTenant () throws NotFoundException {
        Tenant tenant = buildTenant("some-id", "Some Name");
        when(tenantRepository.findById(eq(tenant.getId()))).thenReturn(Optional.of(tenant));
        assertThat(tenantSearchService.findById(tenant.getId())).satisfies(findTenant -> {
            assertThat(findTenant.getId()).isEqualTo(tenant.getId());
            assertThat(findTenant.getName()).isEqualTo(tenant.getName());
        });
        verify(tenantRepository).findById(eq(tenant.getId()));
    }

    @Test
    public void whenFindTenantByIdButNotFoundThenReturnException () {
        when(tenantRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tenantSearchService.findById("some-id"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Tenant not found");
        verify(tenantRepository).findById(anyString());
    }

    @Test
    public void whenFindTenantByIdButIdIsNullThenReturnException () {
        assertThatThrownBy(() -> tenantSearchService.findById(null))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Tenant not found");
        verify(tenantRepository, never()).findById(anyString());
    }

    @Test
    public void whenFindAllTenantsThenReturnTenants () {
        List<Tenant> tenants = List.of(
                buildTenant("some-id", "Some Name"),
                buildTenant("another-id", "Another Name"));
        when(tenantRepository.findAll()).thenReturn(tenants);
        assertThat(tenantSearchService.findAll()).hasSize(2)
                .extracting(Tenant::getId,
                        Tenant::getName)
                .containsExactlyInAnyOrder(
                        tuple("some-id", "Some Name"),
                        tuple("another-id", "Another Name")
                );
        verify(tenantRepository).findAll();
    }

    private Tenant buildTenant (String id, String name) {
        return TenantBuilder.of()
                .id(id)
                .name(name)
                .build();
    }
}
