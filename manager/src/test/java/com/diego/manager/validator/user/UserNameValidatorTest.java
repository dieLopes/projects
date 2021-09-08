package com.diego.manager.validator.user;

import com.diego.manager.builder.UserBuilder;
import com.diego.manager.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserNameValidatorTest {

    private final UserNameValidator userNameValidator = new UserNameValidator();

    @Test
    public void whenTenantNameIsNotNullOrEmptyThenPassWithoutException () {
        assertDoesNotThrow(() -> userNameValidator.validate(UserBuilder.of()
                .name("Some User")
                .address("Some Address")
                .build()));
    }

    @Test
    public void whenTenantNameIsNullThenReturnException () {
        assertThatThrownBy(() -> userNameValidator.validate(UserBuilder.of().address("Some Address").build()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Name is a mandatory field");
    }

    @Test
    public void whenTenantNameIsEmptyThenReturnException () {
        assertThatThrownBy(() -> userNameValidator.validate(UserBuilder.of().name("").address("Some Address").build()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Name is a mandatory field");
    }
}
