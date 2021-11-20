package com.inter.desafiointer.domain;

import com.inter.desafiointer.exception.BadRequestException;

public enum CompanyStatus {

    ACTIVE, INACTIVE;

    public static CompanyStatus of (String status) {
        if (status == null) {
            return null;
        }
        try {
            return CompanyStatus.valueOf(status);
        } catch (Exception e) {
            throw new BadRequestException("Invalid status");
        }
    }
}
