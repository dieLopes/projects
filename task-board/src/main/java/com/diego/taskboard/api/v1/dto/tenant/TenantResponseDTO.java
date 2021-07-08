package com.diego.taskboard.api.v1.dto.tenant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "TenantResponse")
public class TenantResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("Tenant id")
    private String id;
    @ApiModelProperty("Tenant name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
