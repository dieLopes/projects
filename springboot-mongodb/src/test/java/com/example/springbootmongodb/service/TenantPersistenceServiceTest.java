package com.example.springbootmongodb.service;

import com.example.springbootmongodb.builder.TenantBuilder;
import com.example.springbootmongodb.domain.Tenant;
import com.example.springbootmongodb.exception.TenantBadRequestException;
import com.example.springbootmongodb.exception.TenantNotFoundException;
import com.example.springbootmongodb.repository.TenantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
    public void whenCreateTenantWithIdThenReturnException () {
        Tenant tenant = buildTenant();
        assertThatThrownBy(() -> tenantPersistenceService.save(tenant))
                .isInstanceOf(TenantBadRequestException.class)
                .hasMessage("Tenant id must be null");
        verify(tenantRepository, never()).save(any(Tenant.class));
        verify(tenantSearchService, never()).findById(anyString());
    }

    @Test
    public void whenCreateTenantWithoutNameThenReturnException () {
        Tenant tenant = buildTenant();
        tenant.setName(null);
        tenant.setId(null);
        assertThatThrownBy(() -> tenantPersistenceService.save(tenant))
                .isInstanceOf(TenantBadRequestException.class)
                .hasMessage("Name is a mandatory field");
        verify(tenantRepository, never()).save(any(Tenant.class));
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
                .thenThrow(new TenantNotFoundException("Tenant not found"));
        assertThatThrownBy(() ->  tenantPersistenceService.update(tenant.getId(), tenant))
                .isInstanceOf(TenantNotFoundException.class)
                .hasMessage("Tenant not found");
        verify(tenantSearchService).findById(eq(tenant.getId()));
        verify(tenantRepository, never()).save(any(Tenant.class));
    }

    @Test
    public void whenUpdateTenantNotFoundThenReturnException () {
        Tenant tenant = buildTenant();
        when(tenantSearchService.findById(eq(tenant.getId())))
                .thenThrow(new TenantNotFoundException("Tenant not found"));
        assertThatThrownBy(() -> tenantPersistenceService.update(tenant.getId(), tenant))
                .isInstanceOf(TenantNotFoundException.class)
                .hasMessage("Tenant not found");
        verify(tenantSearchService).findById(eq(tenant.getId()));
        verify(tenantRepository, never()).save(any(Tenant.class));
    }

    @Test
    public void whenUpdateTenantWithoutNameThenReturnException () {
        Tenant tenant = buildTenant();
        tenant.setName(null);
        assertThatThrownBy(() -> tenantPersistenceService.update(tenant.getId(), tenant))
                .isInstanceOf(TenantBadRequestException.class)
                .hasMessage("Name is a mandatory field");
        verify(tenantRepository, never()).save(any(Tenant.class));
        verify(tenantSearchService).findById(anyString());
    }

    private Tenant buildTenant () {
        return TenantBuilder.of()
                .id("some-id")
                .name("Some Name")
                .build();
    }
}
