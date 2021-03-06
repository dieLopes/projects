package com.diego.manager.builder;

import com.diego.manager.domain.Tenant;

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
