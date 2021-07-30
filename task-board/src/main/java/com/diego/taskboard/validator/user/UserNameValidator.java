package com.diego.taskboard.validator.user;

import com.diego.taskboard.domain.User;
import com.diego.taskboard.exception.BadRequestException;
import com.diego.taskboard.validator.IValidator;

public class UserNameValidator implements IValidator<User> {

    @Override
    public void validate(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new BadRequestException("Name is a mandatory field");
        }
    }
}
