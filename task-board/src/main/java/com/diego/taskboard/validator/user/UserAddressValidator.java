package com.diego.taskboard.validator.user;

import com.diego.taskboard.domain.User;
import com.diego.taskboard.exception.BadRequestException;
import com.diego.taskboard.validator.IValidator;

public class UserAddressValidator implements IValidator<User> {

    @Override
    public void validate(User user) {
        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            throw new BadRequestException("Address is a mandatory field");
        }
    }
}
