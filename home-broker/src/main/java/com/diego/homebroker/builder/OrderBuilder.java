package com.diego.homebroker.builder;

import com.diego.homebroker.domain.Company;
import com.diego.homebroker.domain.Order;
import com.diego.homebroker.domain.OrderType;
import com.diego.homebroker.domain.Wallet;
import com.diego.homebroker.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderBuilder {

    private final Order order;

    private OrderBuilder() {
        order = new Order();
    }

    public static OrderBuilder of () {
        return new OrderBuilder();
    }

    public OrderBuilder id (String id) {
        order.setId(id);
        return this;
    }

    public OrderBuilder date (LocalDateTime date) {
        order.setDate(date);
        return this;
    }

    public OrderBuilder type (OrderType type) {
        order.setType(type);
        return this;
    }

    public OrderBuilder company (Company company) {
        order.setCompany(company);
        return this;
    }

    public OrderBuilder wallet (Wallet wallet) {
        order.setWallet(wallet);
        return this;
    }

    public OrderBuilder amount (int amount) {
        order.setAmount(amount);
        return this;
    }

    public OrderBuilder unitPrice (BigDecimal unitPrice) {
        order.setUnitPrice(unitPrice);
        return this;
    }

    public OrderBuilder totalPrice (BigDecimal totalPrice) {
        order.setTotalPrice(totalPrice);
        return this;
    }

    public OrderBuilder status (OrderStatus status) {
        order.setStatus(status);
        return this;
    }

    public OrderBuilder code (String code) {
        order.setCode(code);
        return this;
    }

    public OrderBuilder cpf (String cpf) {
        order.setCpf(cpf);
        return this;
    }

    public Order build () {
        return order;
    }
}
