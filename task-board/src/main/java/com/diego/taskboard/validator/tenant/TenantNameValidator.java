package com.diego.taskboard.validator.tenant;

import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.exception.TenantBadRequestException;
import com.diego.taskboard.validator.IValidator;
import org.springframework.stereotype.Service;

@Service
public class TenantNameValidator implements IValidator<Tenant> {

    @Override
    public void validate(Tenant tenant) {
        if (tenant.getName() == null || tenant.getName().isEmpty()) {
            throw new TenantBadRequestException("Name is a mandatory field");
        }
    }
}
