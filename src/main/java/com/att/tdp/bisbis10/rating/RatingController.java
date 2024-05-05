package com.att.tdp.bisbis10.rating;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Object> addRating(@RequestBody RatingDataTransferObject ratingDataTransferObject) {
        try {
            ratingService.addRating(ratingDataTransferObject.getRestaurantId(),ratingDataTransferObject.getRating());
            return ResponseEntity.ok().build();
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
