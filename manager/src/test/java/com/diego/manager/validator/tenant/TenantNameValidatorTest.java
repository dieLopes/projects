package com.diego.manager.validator.tenant;

import com.diego.manager.builder.TenantBuilder;
import com.diego.manager.exception.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TenantNameValidatorTest {

    private final TenantNameValidator tenantNameValidator = new TenantNameValidator();

    @Test
    public void whenTenantNameIsNotNullOrEmptyThenPassWithoutException () {
        tenantNameValidator.validate(TenantBuilder.of().name("Some Tenant").build());
    }

    @Test(expected = BadRequestException.class)
    public void whenTenantNameIsNullThenReturnException () {
        tenantNameValidator.validate(TenantBuilder.of().build());
    }

    @Test(expected = BadRequestException.class)
    public void whenTenantNameIsEmptyThenReturnException () {
        tenantNameValidator.validate(TenantBuilder.of().name("").build());
    }
}