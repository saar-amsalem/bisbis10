package com.att.tdp.bisbis10.rating;

import com.att.tdp.bisbis10.restaurant.Restaurant;
import com.att.tdp.bisbis10.restaurant.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {
    private final RestaurantRepo restaurantRepo;

    @Autowired
    public RatingService(RestaurantRepo restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    public void addRating(Long id, Float rating) {
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(id);
        if (optionalRestaurant.isEmpty()){
            throw new IllegalStateException("No restaurant found with id : " + id);
        }
        Restaurant currentRestaurant = optionalRestaurant.get();
        currentRestaurant.setAverageRating(rating);
        restaurantRepo.save(currentRestaurant);
    }
}
