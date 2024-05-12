package com.att.tdp.bisbis10.dishes;

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
    void addDishSuccess() {
        Long id = 1L;
        DishDataTransferObject dishDTO = new DishDataTransferObject("Pizza", "Delicious pizza", 10L);
        Dish dish = new Dish("Pizza", "Delicious pizza", 10L);

        doNothing().when(dishService).addDish(id, dish);

        ResponseEntity<Object> response = dishController.addDish(id, dishDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateDishSuccess() {
        Long id = 1L;
        Long dishId = 1L;
        DishDataTransferObject dishDTO = new DishDataTransferObject("Pizza", "Delicious pizza", 10L);
        Dish dish = new Dish("Pizza", "Delicious pizza", 10L);
        doNothing().when(dishService).updateDish(id, dishId, dish);

        ResponseEntity<Object> response = dishController.updateDish(id, dishId, dishDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteDishSuccess() {
        Long id = 1L;
        Long dishId = 1L;
        doNothing().when(dishService).deleteDish(id, dishId);

        ResponseEntity<Object> response = dishController.deleteDish(id, dishId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteDishNotFound() {
        Long id = 1L;
        Long dishId = 999L;
        doThrow(new IllegalStateException("Dish not found")).when(dishService).deleteDish(id, dishId);

        ResponseEntity<Object> response = dishController.deleteDish(id, dishId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Dish not found", response.getBody());
    }

    @Test
    void getDishesByRestaurantIdSuccess() {
        Long id = 1L;
        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish("Pizza", "Delicious pizza", 10L));
        when(dishService.getDishesByRestaurant(id)).thenReturn(dishes);

        ResponseEntity<Object> response = dishController.getDishesByRestaurantId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dishes, response.getBody());
    }

    @Test
    void getDishesByRestaurantIdNotFound() {
        Long id = 999L;
        when(dishService.getDishesByRestaurant(id)).thenThrow(new IllegalStateException("Restaurant not found"));

        ResponseEntity<Object> response = dishController.getDishesByRestaurantId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurant not found", response.getBody());
    }
}
