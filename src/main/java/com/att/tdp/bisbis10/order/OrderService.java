package com.att.tdp.bisbis10.order;

import com.att.tdp.bisbis10.dishes.Dish;
import com.att.tdp.bisbis10.orderItem.OrderItem;
import com.att.tdp.bisbis10.restaurant.Restaurant;
import com.att.tdp.bisbis10.restaurant.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final RestaurantRepo restaurantRepo;
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(RestaurantRepo restaurantRepo, OrderRepo orderRepo) {
        this.restaurantRepo = restaurantRepo;
        this.orderRepo = orderRepo;
    }

    public UUID createOrder(Order order) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(order.getRestaurantId());
        if (optionalRestaurant.isEmpty()){
            throw new IllegalStateException("No restaurant found with id : " + order.getRestaurantId());
        }
        Restaurant restaurant = optionalRestaurant.get();
        List<Dish> dishes = restaurant.getDishes();
        for (OrderItem orderItem : order.getOrderItems()) {
            Optional<Dish> optionalDishToUpdate = dishes.stream().filter(dishOfList -> dishOfList.getID().equals(orderItem.getDishId())).findFirst();
            if (optionalDishToUpdate.isEmpty()) {
                throw new IllegalStateException("No dish found with id : " + orderItem.getDishId() + " at this restaurant, order is not allowed !");
            }
        }
        Order managedOrder = new Order(order.getRestaurantId(), order.getOrderItems());
        orderRepo.save(managedOrder);

        List<Order> orders = restaurant.getOrders();
        orders.add(managedOrder);
        restaurant.setOrders(orders);
        return managedOrder.getOrderId();
    }
}
