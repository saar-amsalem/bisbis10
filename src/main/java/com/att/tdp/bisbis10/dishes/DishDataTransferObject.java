package com.att.tdp.bisbis10.dishes;

public class DishDataTransferObject {
    private final String name;
    private final String description;
    private final Long price;

    public DishDataTransferObject(String name, String description, Long price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getPrice() {
        return this.price;
    }
}
