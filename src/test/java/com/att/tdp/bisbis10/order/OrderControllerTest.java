package com.att.tdp.bisbis10.order;

import com.att.tdp.bisbis10.order.*;
import com.att.tdp.bisbis10.orderItem.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createOrder_Success() {
        // Arrange
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem(12L,1);
        OrderItem orderItem2 = new OrderItem(13L,5);
        Collections.addAll(orderItems, orderItem1, orderItem2);
        Order order = new Order(1L,orderItems);
        when(orderService.createOrder(order)).thenReturn(order.getOrderId());

        // Act
        ResponseEntity<Object> response = orderController.createOrder(order);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order.getOrderId(), response.getBody());
    }

    @Test
    void createOrder_Failure_NotFound() {
        // Arrange
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem(12L,1);
        OrderItem orderItem2 = new OrderItem(13L,5);
        Collections.addAll(orderItems, orderItem1, orderItem2);
        Order order = new Order(999L,orderItems);
        when(orderService.createOrder(order)).thenThrow(new IllegalStateException("Order not found"));

        // Act
        ResponseEntity<Object> response = orderController.createOrder(order);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order not found", response.getBody());
    }

    @Test
    void createOrder_Failure_InternalServerError() {
        // Arrange
        Order order = new Order();
        when(orderService.createOrder(order)).thenThrow(new RuntimeException("Internal server error"));

        // Act
        ResponseEntity<Object> response = orderController.createOrder(order);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody());
    }
}
