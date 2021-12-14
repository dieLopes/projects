package com.diego.homebroker.api.v1.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "OrderResponseRandomList")
public class OrderResponseRandomDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("List of orders")
    private List<OrderResponseDTO> orders;
    @ApiModelProperty("Total of orders")
    private BigDecimal total;
    @ApiModelProperty("Change")
    private BigDecimal change;

    public OrderResponseRandomDTO() { }

    public OrderResponseRandomDTO(List<OrderResponseDTO> orders) {
        this.orders = orders;
    }

    public List<OrderResponseDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderResponseDTO> orders) {
        this.orders = orders;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }
}