package com.att.tdp.bisbis10.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllRestaurants(@RequestParam(required = false) String cuisine) {
        try {
            if (cuisine != null) {
                return ResponseEntity.status(200).body(restaurantService.getRestaurantsByCuisine(cuisine));
            }
            return ResponseEntity.status(200).body(restaurantService.getAllRestaurants());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRestaurant(@PathVariable Long id) {
        try {
            Restaurant restaurant = restaurantService.getRestaurantByID(id);
            return ResponseEntity.status(200).body(restaurant);
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(404).body("Restaurant Not Found !");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> addRestaurant(@RequestBody Restaurant restaurant) {
        try {
            return ResponseEntity.status(201).body(restaurantService.addRestaurant(restaurant));
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        try {
            restaurantService.updateRestaurant(id, restaurant);
            return ResponseEntity.status(200).body(restaurant);
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRestaurant(@PathVariable Long id) {
        try {
            restaurantService.deleteRestaurant(id);
            return ResponseEntity.noContent().build();
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
