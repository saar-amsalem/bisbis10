package com.att.tdp.bisbis10.orderItem;

import jakarta.persistence.*;

@Entity
@Table
public class OrderItem {
    @Id
    private Long dishId;
    private Integer amount;

    public OrderItem() {
    }

    public OrderItem(Long dishId, Integer amount) {
        this.dishId = dishId;
        this.amount = amount;
    }

    public Long getDishId() {
        return this.dishId;
    }

    public Integer getAmount() {
        return amount;
    }
}

