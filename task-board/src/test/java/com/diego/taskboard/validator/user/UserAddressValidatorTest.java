package com.diego.taskboard.validator.user;

import com.diego.taskboard.builder.UserBuilder;
import com.diego.taskboard.exception.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserAddressValidatorTest {

    private final UserAddressValidator userAddressValidator = new UserAddressValidator();

    @Test
    public void whenTenantNameIsNotNullOrEmptyThenPassWithoutException () {
        userAddressValidator.validate(UserBuilder.of()
                .name("Some Name")
                .address("Some Address")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void whenTenantNameIsNullThenReturnException () {
        userAddressValidator.validate(UserBuilder.of()
                .name("Some Name")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void whenTenantNameIsEmptyThenReturnException () {
        userAddressValidator.validate(UserBuilder.of()
                .name("Some Name")
                .address("")
                .build());
    }
}