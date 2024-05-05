package com.att.tdp.bisbis10.order;

import com.att.tdp.bisbis10.orderItem.OrderItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private UUID orderId;
    @JsonProperty("restaurantId")
    @JoinColumn(name = "restaurantId")
    private Long restaurantId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {
    }

    public Order(Long restaurantId, List<OrderItem> orderItems) {
        this.restaurantId = restaurantId;
        this.orderId = UUID.randomUUID();
        for (OrderItem orderItem : orderItems) {
            OrderItem orderToSave = new OrderItem(orderItem.getDishId(), orderItem.getAmount());
            this.orderItems.add(orderToSave);
        }
    }

    public UUID getOrderId() {
        return this.orderId;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }
}
