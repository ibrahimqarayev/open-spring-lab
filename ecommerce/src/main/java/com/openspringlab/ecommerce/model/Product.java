package com.openspringlab.ecommerce.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Product {

    @NotBlank(message = "Name is required") // Name must not be empty
    private String name;

    @Min(value = 0, message = "Price must be non-negative") // Price must be >= 0
    private double price;

    // Constructors
    public Product() {}
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
