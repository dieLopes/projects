package com.diego.manager.validator.tenant;

import com.diego.manager.builder.TenantBuilder;
import com.diego.manager.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TenantNameValidatorTest {

    private final TenantNameValidator tenantNameValidator = new TenantNameValidator();

    @Test
    public void whenTenantNameIsNotNullOrEmptyThenPassWithoutException () {
        assertDoesNotThrow(() -> tenantNameValidator.validate(TenantBuilder.of()
                .name("Some Tenant")
                .build()));
    }

    @Test
    public void whenTenantNameIsNullThenReturnException () {
        assertThatThrownBy(() -> tenantNameValidator.validate(TenantBuilder.of().build()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Name is a mandatory field");
    }

    @Test
    public void whenTenantNameIsEmptyThenReturnException () {
        assertThatThrownBy(() -> tenantNameValidator.validate(TenantBuilder.of().name("").build()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Name is a mandatory field");
    }
}