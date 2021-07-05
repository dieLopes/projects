package com.example.springbootmongodb.api.v1.dto.tenant;

import java.io.Serializable;

public class TenantUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {

        private TenantUpdateDTO tenant;

        private Builder() {
            tenant = new TenantUpdateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder name (String name) {
            tenant.setName(name);
            return this;
        }

        public TenantUpdateDTO build () {
            return tenant;
        }
    }
}
