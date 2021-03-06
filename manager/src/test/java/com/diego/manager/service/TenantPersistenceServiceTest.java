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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TenantPersistenceServiceTest {

    @InjectMocks
    private TenantPersistenceService tenantPersistenceService;
    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private TenantSearchService tenantSearchService;

    @Test
    public void whenCreateTenantThenSaveTenant () {
        Tenant tenant = buildTenant();
        tenant.setId(null);
        when(tenantRepository.save(eq(tenant))).thenReturn(tenant);
        tenantPersistenceService.save(tenant);
        verify(tenantRepository).save(any(Tenant.class));
    }

    @Test
    public void whenUpdateTenantThenSaveTenant () {
        Tenant tenant = buildTenant();
        when(tenantRepository.save(eq(tenant))).thenReturn(tenant);
        tenantPersistenceService.update(tenant.getId(), tenant);
        verify(tenantRepository).save(any(Tenant.class));
    }

    @Test
    public void whenUpdateTenantWithoutIdThenReturnException () {
        Tenant tenant = buildTenant();
        tenant.setId(null);
        when(tenantSearchService.findById(eq(null)))
                .thenThrow(new NotFoundException("Tenant not found"));
        assertThatThrownBy(() ->  tenantPersistenceService.update(tenant.getId(), tenant))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Tenant not found");
        verify(tenantSearchService).findById(eq(tenant.getId()));
        verify(tenantRepository, never()).save(any(Tenant.class));
    }

    @Test
    public void whenUpdateTenantNotFoundThenReturnException () {
        Tenant tenant = buildTenant();
        when(tenantSearchService.findById(eq(tenant.getId())))
                .thenThrow(new NotFoundException("Tenant not found"));
        assertThatThrownBy(() -> tenantPersistenceService.update(tenant.getId(), tenant))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Tenant not found");
        verify(tenantSearchService).findById(eq(tenant.getId()));
        verify(tenantRepository, never()).save(any(Tenant.class));
    }

    private Tenant buildTenant () {
        return TenantBuilder.of()
                .id("some-id")
                .name("Some Name")
                .build();
    }
}
