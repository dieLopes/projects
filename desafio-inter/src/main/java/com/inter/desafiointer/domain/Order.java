package com.inter.desafiointer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "B_ORDER")
public class Order implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(columnDefinition = "TIMESTAMP", name = "ORDER_DATE", nullable = false)
    private LocalDateTime date;
    @Column(name = "ORDER_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;
    @Column(name = "CODE", nullable = false)
    private String code;
    @Column(name = "AMOUNT", nullable = false)
    private int amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
