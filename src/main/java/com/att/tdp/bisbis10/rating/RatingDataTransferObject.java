package com.att.tdp.bisbis10.rating;

public class RatingDataTransferObject {
    private final Long restaurantId;
    private final Float rating;

    public RatingDataTransferObject(Long restaurantId, Float rating) {
        this.restaurantId = restaurantId;
        this.rating = rating;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public Float getRating() {
        return rating;
    }
}
