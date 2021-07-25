package com.diego.taskboard.api.v1.dto.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "TenantCreate")
public class TenantCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "Tenant name", required = true)
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

        private final TenantCreateDTO tenant;

        private Builder() {
            tenant = new TenantCreateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder name (String name) {
            tenant.name = name;
            return this;
        }

        public TenantCreateDTO build () {
            return tenant;
        }
    }
}
