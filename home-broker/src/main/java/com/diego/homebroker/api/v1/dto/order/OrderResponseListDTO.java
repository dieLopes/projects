package com.diego.homebroker.api.v1.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "OrderResponseList")
public class OrderResponseListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of orders")
    private List<OrderResponseDTO> orders;

    public OrderResponseListDTO() { }

    public OrderResponseListDTO(List<OrderResponseDTO> orders) {
        this.orders = orders;
    }

    public List<OrderResponseDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderResponseDTO> orders) {
        this.orders = orders;
    }
}