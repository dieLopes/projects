package com.diego.taskboard.validator.tenant;

import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.exception.BadRequestException;
import com.diego.taskboard.validator.IValidator;

public class TenantNameValidator implements IValidator<Tenant> {

    @Override
    public void validate(Tenant tenant) {
        if (tenant.getName() == null || tenant.getName().isEmpty()) {
            throw new BadRequestException("Name is a mandatory field");
        }
    }
}
