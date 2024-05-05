package com.att.tdp.bisbis10.dishes;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class DishController {
    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping("/{id}/dish")
    public ResponseEntity<Object> addDish(@PathVariable Long id, @RequestBody DishDataTransferObject dishDataTransferObject) {
        try {
            Dish dish = new Dish(dishDataTransferObject.getName(),dishDataTransferObject.getDescription(),dishDataTransferObject.getPrice());
            dishService.addDish(id, dish);
            return ResponseEntity.status(201).body(dish);
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/dish/{dishId}")
    public ResponseEntity<Object> updateDish(@PathVariable Long id,@PathVariable Long dishId, @RequestBody DishDataTransferObject dishDataTransferObject) {
        try {
            Dish dish = new Dish(dishDataTransferObject.getName(),dishDataTransferObject.getDescription(),dishDataTransferObject.getPrice());
            dishService.updateDish(id, dishId, dish);
            return ResponseEntity.ok().build();
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/dish/{dishId}")
    public ResponseEntity<Object> deleteDish(@PathVariable Long id,@PathVariable Long dishId) {
        try {
            dishService.deleteDish(id, dishId);
            return ResponseEntity.noContent().build();
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/dish")
    public ResponseEntity<Object> getDishesByRestaurantId(@PathVariable Long id) {
        try {
            return ResponseEntity.status(200).body(dishService.getDishesByRestaurant(id));
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
