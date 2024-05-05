package com.att.tdp.bisbis10.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    public Optional<List<Restaurant>> findByCuisinesContains(String cuisine);
}
