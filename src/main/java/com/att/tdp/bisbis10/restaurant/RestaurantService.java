package com.att.tdp.bisbis10.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepo restaurantRepo;

    @Autowired
    public RestaurantService(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    public Restaurant getRestaurantByID(Long id) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(id);
        if (restaurant.isEmpty()) {
            throw new IllegalStateException("No restaurant found with id : " + id);
        }
        return restaurant.get();
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        try {
            return restaurantRepo.save(restaurant);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Restaurant name already taken, you are not allowed to use it !");
        }
    }

    public void updateRestaurant(Long id, Restaurant restaurant) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(id);
        if (optionalRestaurant.isEmpty()){
            throw new IllegalStateException("No restaurant found with id : " + id);
        }
        Restaurant currentRestaurant = optionalRestaurant.get();
        if (restaurant.getName() != null && !restaurant.getName().equals(currentRestaurant.getName())) {
            currentRestaurant.setName(restaurant.getName());
        }
        if (restaurant.getIsKosher() != null && !restaurant.getIsKosher().equals(currentRestaurant.getIsKosher())) {
            currentRestaurant.setIsKosher(restaurant.getIsKosher());
        }
        if (restaurant.getCuisines() != null && !restaurant.getCuisines().equals(currentRestaurant.getCuisines())) {
            currentRestaurant.setCuisines(restaurant.getCuisines());
        }
        restaurantRepo.save(currentRestaurant);
    }

    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        Optional<List<Restaurant>> restaurants = restaurantRepo.findByCuisinesContains(cuisine);
        if (restaurants.isEmpty()) {
            throw new IllegalStateException("No restaurants found for : " + cuisine);
        }
        return restaurants.get();
    }

    public void deleteRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
        if(restaurant.isEmpty()) {
            throw new IllegalStateException("No restaurant found with id : " + restaurantId);
        }
        restaurantRepo.deleteById(restaurantId);
    }

}
