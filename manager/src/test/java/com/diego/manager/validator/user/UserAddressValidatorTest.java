package com.diego.manager.validator.user;

import com.diego.manager.builder.UserBuilder;
import com.diego.manager.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserAddressValidatorTest {

    private final UserAddressValidator userAddressValidator = new UserAddressValidator();

    @Test
    public void whenTenantNameIsNotNullOrEmptyThenPassWithoutException () {
        assertDoesNotThrow(() -> userAddressValidator.validate(UserBuilder.of()
                .name("Some Name")
                .address("Some Address")
                .build()));
    }

    @Test
    public void whenTenantNameIsNullThenReturnException () {
        assertThatThrownBy(() -> userAddressValidator.validate(UserBuilder.of().name("Some Name").build()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Address is a mandatory field");
    }

    @Test
    public void whenTenantNameIsEmptyThenReturnException () {
        assertThatThrownBy(() -> userAddressValidator.validate(UserBuilder.of().name("Some Name").address("").build()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Address is a mandatory field");
    }
}
