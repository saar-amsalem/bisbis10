package com.att.tdp.bisbis10.dishes;

import com.att.tdp.bisbis10.dishes.*;
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

class DishControllerTest {

    @Mock
    private DishService dishService;

    @InjectMocks
    private DishController dishController;

    public DishControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addDish_Success() {
        // Arrange
        Long id = 1L;
        DishDataTransferObject dishDTO = new DishDataTransferObject("Pizza", "Delicious pizza", 10L);
        Dish dish = new Dish("Pizza", "Delicious pizza", 10L);

        // Mock the behavior of dishService.addDish to do nothing
        doNothing().when(dishService).addDish(id, dish);

        // Act
        ResponseEntity<Object> response = dishController.addDish(id, dishDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateDish_Success() {
        // Arrange
        Long id = 1L;
        Long dishId = 1L;
        DishDataTransferObject dishDTO = new DishDataTransferObject("Pizza", "Delicious pizza", 10L);
        Dish dish = new Dish("Pizza", "Delicious pizza", 10L);
        doNothing().when(dishService).updateDish(id, dishId, dish);

        // Act
        ResponseEntity<Object> response = dishController.updateDish(id, dishId, dishDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteDish_Success() {
        // Arrange
        Long id = 1L;
        Long dishId = 1L;
        doNothing().when(dishService).deleteDish(id, dishId);

        // Act
        ResponseEntity<Object> response = dishController.deleteDish(id, dishId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteDish_NotFound() {
        // Arrange
        Long id = 1L;
        Long dishId = 999L; // Dish ID that does not exist
        doThrow(new IllegalStateException("Dish not found")).when(dishService).deleteDish(id, dishId);

        // Act
        ResponseEntity<Object> response = dishController.deleteDish(id, dishId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Dish not found", response.getBody());
    }

    @Test
    void getDishesByRestaurantId_Success() {
        // Arrange
        Long id = 1L;
        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish("Pizza", "Delicious pizza", 10L));
        when(dishService.getDishesByRestaurant(id)).thenReturn(dishes);

        // Act
        ResponseEntity<Object> response = dishController.getDishesByRestaurantId(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dishes, response.getBody());
    }

    @Test
    void getDishesByRestaurantId_NotFound() {
        // Arrange
        Long id = 999L; // Restaurant ID that does not exist
        when(dishService.getDishesByRestaurant(id)).thenThrow(new IllegalStateException("Restaurant not found"));

        // Act
        ResponseEntity<Object> response = dishController.getDishesByRestaurantId(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurant not found", response.getBody());
    }
}
