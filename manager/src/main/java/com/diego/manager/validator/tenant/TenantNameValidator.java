package com.diego.manager.validator.tenant;

import com.diego.manager.domain.Tenant;
import com.diego.manager.exception.BadRequestException;
import com.diego.manager.validator.IValidator;

public class TenantNameValidator implements IValidator<Tenant> {

    @Override
    public void validate(Tenant tenant) {
        if (tenant.getName() == null || tenant.getName().isEmpty()) {
            throw new BadRequestException("Name is a mandatory field");
        }
    }
}
