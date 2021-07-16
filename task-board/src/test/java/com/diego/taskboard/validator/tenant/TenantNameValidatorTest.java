package com.diego.taskboard.validator.tenant;

import com.diego.taskboard.builder.TenantBuilder;
import com.diego.taskboard.exception.TenantBadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TenantNameValidatorTest {

    @InjectMocks
    private TenantNameValidator tenantNameValidator;

    @Test
    public void whenTenantNameIsNotNullOrEmptyThenPassWithoutException () {
        tenantNameValidator.validate(TenantBuilder.of().name("Some Tenant").build());
    }

    @Test(expected = TenantBadRequestException.class)
    public void whenTenantNameIsNullThenReturnException () {
        tenantNameValidator.validate(TenantBuilder.of().build());
    }

    @Test(expected = TenantBadRequestException.class)
    public void whenTenantNameIsEmptyThenReturnException () {
        tenantNameValidator.validate(TenantBuilder.of().name("").build());
    }
}