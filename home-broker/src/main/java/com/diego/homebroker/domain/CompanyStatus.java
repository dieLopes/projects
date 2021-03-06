package com.diego.homebroker.domain;

import com.diego.homebroker.exception.BadRequestException;

public enum CompanyStatus {

    ACTIVE, INACTIVE;

    public static CompanyStatus of (String status) {
        try {
            return CompanyStatus.valueOf(status);
        } catch (Exception e) {
            throw new BadRequestException("Invalid status");
        }
    }
}
