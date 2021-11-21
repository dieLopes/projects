package com.inter.desafiointer.api.v1.dto.user;

import com.inter.desafiointer.api.v1.dto.wallet.WalletResponseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "UserResponse")
public class UserResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("User id")
    private String id;
    @ApiModelProperty("User name")
    private String name;
    @ApiModelProperty("User cpf")
    private String cpf;
    @ApiModelProperty("User wallet")
    private WalletResponseDTO wallet;

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

    public WalletResponseDTO getWallet() {
        return wallet;
    }

    public void setWallet(WalletResponseDTO wallet) {
        this.wallet = wallet;
    }
}
