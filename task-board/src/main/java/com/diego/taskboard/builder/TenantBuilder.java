package com.diego.taskboard.builder;

import com.diego.taskboard.domain.Tenant;

public class TenantBuilder {

    private Tenant tenant;

    private TenantBuilder() {
        tenant = new Tenant();
    }

    public static TenantBuilder of () {
        return new TenantBuilder();
    }

    public TenantBuilder id (String id) {
        tenant.setId(id);
        return this;
    }

    public TenantBuilder name (String name) {
        tenant.setName(name);
        return this;
    }

    public Tenant build () {
        return tenant;
    }
}
