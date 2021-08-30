package com.diego.manager.validator.user;

import com.diego.manager.domain.User;
import com.diego.manager.exception.BadRequestException;
import com.diego.manager.validator.IValidator;

public class UserNameValidator implements IValidator<User> {

    @Override
    public void validate(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new BadRequestException("Name is a mandatory field");
        }
    }
}
