package com.att.tdp.bisbis10.dishes;

import com.att.tdp.bisbis10.restaurant.Restaurant;
import com.att.tdp.bisbis10.restaurant.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    private final RestaurantRepo restaurantRepo;

    @Autowired
    public DishService(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    public void addDish(Long restaurantId, Dish dish) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            throw new IllegalStateException("No restaurant found with id : " + restaurantId);
        }
        Restaurant restaurant = optionalRestaurant.get();
        List<Dish> dishes = restaurant.getDishes();
        dishes.add(dish);
        restaurant.setDishes(dishes);
        restaurantRepo.save(restaurant);
    }

    public void updateDish(Long restaurantId, Long dishId, Dish dish) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            throw new IllegalStateException("No restaurant found with id : " + restaurantId);
        }
        Restaurant restaurant = optionalRestaurant.get();
        List<Dish> dishes = restaurant.getDishes();
        Optional<Dish> optionalDishToUpdate = dishes.stream().filter(dishOfList -> dishOfList.getID().equals(dishId)).findFirst();
        if (optionalDishToUpdate.isEmpty()) {
            throw new IllegalStateException("No dish found with id : " + dishId);
        }
        Dish dishToUpdate = optionalDishToUpdate.get();
        if (dish.getName() != null && !dishToUpdate.getName().equals(dish.getName())){
            dishToUpdate.setName(dish.getName());
        }
        if (dish.getDescription() != null && !dishToUpdate.getDescription().equals(dish.getDescription())){
            dishToUpdate.setDescription(dish.getDescription());
        }
        if (dish.getPrice() != null && !dishToUpdate.getPrice().equals(dish.getPrice())){
            dishToUpdate.setPrice(dish.getPrice());
        }
        restaurantRepo.save(restaurant);
    }

    public void deleteDish(Long restaurantId, Long dishId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            throw new IllegalStateException("No restaurant found with id : " + restaurantId);
        }
        Restaurant restaurant = optionalRestaurant.get();
        List<Dish> dishes = restaurant.getDishes();
        boolean removed = dishes.removeIf(dish -> dish.getID().equals(dishId));
        if (!removed) {
            throw new IllegalStateException("No dish found with id : " + dishId + " in restaurant with id : " + restaurantId);
        }
        restaurantRepo.save(restaurant);
    }

    public List<Dish> getDishesByRestaurant(Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            throw new IllegalStateException("No restaurant found with id : " + restaurantId);
        }
        Restaurant restaurant = optionalRestaurant.get();
        return restaurant.getDishes();
    }
}
