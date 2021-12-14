package com.diego.homebroker.api.v1.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "UserCreate")
public class UserCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "User name", required = true)
    private String name;
    @ApiModelProperty(value = "User cpf", required = true)
    private String cpf;

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

    public static class Builder {

        private final UserCreateDTO user;

        private Builder() {
            user = new UserCreateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder name (String name) {
            user.name = name;
            return this;
        }

        public Builder cpf (String cpf) {
            user.cpf = cpf;
            return this;
        }

        public UserCreateDTO build () {
            return user;
        }
    }
}
