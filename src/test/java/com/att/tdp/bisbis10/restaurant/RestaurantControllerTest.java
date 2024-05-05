package com.att.tdp.bisbis10.restaurant;

import com.att.tdp.bisbis10.restaurant.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllRestaurants_WithCuisine() {
        // Arrange
        String cuisine = "Asian";
        List<String> cuisines = new ArrayList<>();
        cuisines.add(cuisine);
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant("Asian Restaurant",false, cuisines);
        restaurant.setId(1L);
        restaurants.add(restaurant);
        when(restaurantService.getRestaurantsByCuisine(cuisine)).thenReturn(restaurants);

        // Act
        ResponseEntity<Object> response = restaurantController.getAllRestaurants(cuisine);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
    }

    @Test
    void getAllRestaurants_WithoutCuisine() {
        // Arrange
        List<Restaurant> restaurants = new ArrayList<>();
        String cuisine1 = "Italian";
        String cuisine2 = "Spanish";
        List<String> cuisines1 = new ArrayList<>();
        List<String> cuisines2 = new ArrayList<>();
        cuisines1.add(cuisine1);
        cuisines2.add(cuisine2);
        Restaurant restaurant1 = new Restaurant("Italian Restaurant",false, cuisines1);
        restaurant1.setId(1L);
        Restaurant restaurant2 = new Restaurant("Spanish Restaurant",false, cuisines2);
        restaurant2.setId(2L);
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        // Act
        ResponseEntity<Object> response = restaurantController.getAllRestaurants(null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurants, response.getBody());
    }

    @Test
    void getRestaurant_ExistingId() {
        // Arrange
        Long id = 1L;
        String cuisine = "Indian";
        List<String> cuisines = new ArrayList<>();
        cuisines.add(cuisine);
        Restaurant restaurant = new Restaurant("Indian Restaurant",false, cuisines);
        restaurant.setId(id);
        when(restaurantService.getRestaurantByID(id)).thenReturn(restaurant);

        // Act
        ResponseEntity<Object> response = restaurantController.getRestaurant(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurant, response.getBody());
    }

    @Test
    void getRestaurant_NonExistingId() {
        // Arrange
        Long id = 999L;
        when(restaurantService.getRestaurantByID(id)).thenThrow(new IllegalStateException("Restaurant Not Found !"));

        // Act
        ResponseEntity<Object> response = restaurantController.getRestaurant(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurant Not Found !", response.getBody());
    }

    @Test
    void addRestaurant_Success() {
        // Arrange
        String cuisine = "Israeli";
        List<String> cuisines = new ArrayList<>();
        cuisines.add(cuisine);
        Restaurant restaurant = new Restaurant("Israeli Restaurant",true, cuisines);
        restaurant.setId(1L);
        when(restaurantService.addRestaurant(restaurant)).thenReturn(restaurant);

        // Act
        ResponseEntity<Object> response = restaurantController.addRestaurant(restaurant);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restaurant, response.getBody());
    }

    @Test
    void addRestaurant_Conflict() {
        // Arrange
        String cuisine = "Israeli";
        List<String> cuisines = new ArrayList<>();
        cuisines.add(cuisine);
        Restaurant restaurant = new Restaurant("Israeli Restaurant",true, cuisines);
        restaurant.setId(1L);
        when(restaurantService.addRestaurant(restaurant)).thenThrow(new IllegalStateException("Restaurant already exists !"));

        // Act
        ResponseEntity<Object> response = restaurantController.addRestaurant(restaurant);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Restaurant already exists !", response.getBody());
    }

    @Test
    void updateRestaurant_ExistingId() {
        // Arrange
        String cuisine = "Israeli";
        List<String> cuisines = new ArrayList<>();
        cuisines.add(cuisine);
        Restaurant restaurant = new Restaurant("Israeli Restaurant Not Kosher",false, cuisines);
        restaurant.setId(1L);
        doNothing().when(restaurantService).updateRestaurant(1L, restaurant);

        // Act
        ResponseEntity<Object> response = restaurantController.updateRestaurant(1L, restaurant);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurant, response.getBody());
    }

    @Test
    void updateRestaurant_NonExistingId() {
        // Arrange
        Long id = 999L;
        String cuisine = "Israeli";
        List<String> cuisines = new ArrayList<>();
        cuisines.add(cuisine);
        Restaurant restaurant = new Restaurant("Israeli Restaurant Not Kosher",false, cuisines);
        restaurant.setId(id);
        doThrow(new IllegalStateException("Restaurant Not Found !")).when(restaurantService).updateRestaurant(id, restaurant);

        // Act
        ResponseEntity<Object> response = restaurantController.updateRestaurant(id, restaurant);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurant Not Found !", response.getBody());
    }

    @Test
    void deleteRestaurant_ExistingId() {
        // Arrange
        Long id = 1L;
        doNothing().when(restaurantService).deleteRestaurant(id);

        // Act
        ResponseEntity<Object> response = restaurantController.deleteRestaurant(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteRestaurant_NonExistingId() {
        // Arrange
        Long id = 999L;
        doThrow(new IllegalStateException("Restaurant Not Found !")).when(restaurantService).deleteRestaurant(id);

        // Act
        ResponseEntity<Object> response = restaurantController.deleteRestaurant(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurant Not Found !", response.getBody());
    }
}
