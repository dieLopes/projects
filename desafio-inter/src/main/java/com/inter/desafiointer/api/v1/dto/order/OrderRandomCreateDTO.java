package com.inter.desafiointer.api.v1.dto.order;

import com.inter.desafiointer.api.v1.dto.user.UserCreateDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "OrderRandomCreate")
public class OrderRandomCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of orders")
    private String walletId;
    @ApiModelProperty("Total for orders")
    private BigDecimal total;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public static class Builder {

        private final OrderRandomCreateDTO order;

        private Builder() {
            order = new OrderRandomCreateDTO();
        }

        public static Builder of () {
            return new Builder();
        }

        public Builder walletId (String walletId) {
            order.walletId = walletId;
            return this;
        }

        public Builder total (BigDecimal total) {
            order.total = total;
            return this;
        }

        public OrderRandomCreateDTO build () {
            return order;
        }
    }
}