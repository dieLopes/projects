package com.diego.manager.validator.user;

import com.diego.manager.domain.User;
import com.diego.manager.exception.BadRequestException;
import com.diego.manager.validator.IValidator;

public class UserAddressValidator implements IValidator<User> {

    @Override
    public void validate(User user) {
        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            throw new BadRequestException("Address is a mandatory field");
        }
    }
}
