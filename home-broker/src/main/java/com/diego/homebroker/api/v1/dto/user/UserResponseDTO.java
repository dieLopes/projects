package com.diego.homebroker.api.v1.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "UserResponse")
@JsonIgnoreProperties("wallet.balance")
public class UserResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("User id")
    private String id;
    @ApiModelProperty("User name")
    private String name;
    @ApiModelProperty("User cpf")
    private String cpf;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
