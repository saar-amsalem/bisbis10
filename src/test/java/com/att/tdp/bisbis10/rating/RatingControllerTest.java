package com.att.tdp.bisbis10.rating;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addRating_Success() {
        // Arrange
        RatingDataTransferObject ratingData = new RatingDataTransferObject(1L, 4.5F);
        doNothing().when(ratingService).addRating(1L, 4.5F);

        // Act
        ResponseEntity<Object> response = ratingController.addRating(ratingData);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void addRating_RestaurantNotFound() {
        // Arrange
        RatingDataTransferObject ratingData = new RatingDataTransferObject(1L, 4.5F);
        doThrow(new IllegalStateException("Restaurant not found"))
                .when(ratingService).addRating(1L, 4.5F);

        // Act
        ResponseEntity<Object> response = ratingController.addRating(ratingData);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Restaurant not found", response.getBody());
    }


    @Test
    void addRating_Exception() {
        // Arrange
        RatingDataTransferObject ratingData = new RatingDataTransferObject(1L, 4.5F);
        doThrow(new RuntimeException("Something went wrong"))
                .when(ratingService).addRating(1L, 4.5F);

        // Act
        ResponseEntity<Object> response = ratingController.addRating(ratingData);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went wrong", response.getBody());
    }

}
