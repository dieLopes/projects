package com.diego.taskboard.validator.user;

import com.diego.taskboard.builder.UserBuilder;
import com.diego.taskboard.exception.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserNameValidatorTest {

    @InjectMocks
    private UserNameValidator userNameValidator;

    @Test
    public void whenTenantNameIsNotNullOrEmptyThenPassWithoutException () {
        userNameValidator.validate(UserBuilder.of()
                .name("Some User")
                .address("Some Address")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void whenTenantNameIsNullThenReturnException () {
        userNameValidator.validate(UserBuilder.of()
                .address("Some Address")
                .build());
    }

    @Test(expected = BadRequestException.class)
    public void whenTenantNameIsEmptyThenReturnException () {
        userNameValidator.validate(UserBuilder.of()
                .name("")
                .address("Some Address")
                .build());
    }
}
