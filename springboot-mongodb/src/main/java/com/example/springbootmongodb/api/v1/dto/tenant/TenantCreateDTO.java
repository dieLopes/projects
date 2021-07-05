package com.example.springbootmongodb.api.v1.dto.tenant;

import java.io.Serializable;

public class TenantCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TenantCreateDTO{" +
                "name='" + name + '\'' +
                '}';
    }

    public static class Builder {

        private TenantCreateDTO tenant;

        private Builder() {
            tenant = new TenantCreateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder name (String name) {
            tenant.setName(name);
            return this;
        }

        public TenantCreateDTO build () {
            return tenant;
        }
    }
}
