package com.diego.taskboard.service;

import com.diego.taskboard.builder.TenantBuilder;
import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.exception.TenantNotFoundException;
import com.diego.taskboard.repository.TenantRepository;
import com.diego.taskboard.validator.IValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TenantPersistenceServiceTest {

    @InjectMocks
    private TenantPersistenceService tenantPersistenceService;
    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private TenantSearchService tenantSearchService;
    @Mock
    private List<IValidator<Tenant>> validators;

    @Test
    public void whenCreateTenantThenSaveTenant () {
        Tenant tenant = buildTenant();
        tenant.setId(null);
        when(tenantRepository.save(eq(tenant))).thenReturn(tenant);
        tenantPersistenceService.save(tenant);
        verify(tenantRepository).save(any(Tenant.class));
        verify(validators).forEach(any(Consumer.class));
    }

    @Test
    public void whenUpdateTenantThenSaveTenant () {
        Tenant tenant = buildTenant();
        when(tenantRepository.save(eq(tenant))).thenReturn(tenant);
        tenantPersistenceService.update(tenant.getId(), tenant);
        verify(tenantRepository).save(any(Tenant.class));
        verify(validators).forEach(any(Consumer.class));
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
        verifyNoInteractions(validators);
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
        verifyNoInteractions(validators);
    }

    private Tenant buildTenant () {
        return TenantBuilder.of()
                .id("some-id")
                .name("Some Name")
                .build();
    }
}
