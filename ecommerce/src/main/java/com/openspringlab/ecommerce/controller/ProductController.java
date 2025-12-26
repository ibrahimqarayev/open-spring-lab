package com.openspringlab.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openspringlab.ecommerce.model.Product;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products") // Base path for all endpoints
public class ProductController {

    private List<Product> products = new ArrayList<>(); // In-memory storage

    // POST endpoint with validation
    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody Product product, BindingResult result) {
        if(result.hasErrors()) {
            // Return the first validation error message
            return ResponseEntity.badRequest()
                                 .body(result.getAllErrors().get(0).getDefaultMessage());
        }

        products.add(product);
        return ResponseEntity.ok("Product is valid: " + product.getName() + " - " + product.getPrice());
    }

    // GET endpoint
    @GetMapping
    public List<Product> getProducts() {
        return products;
    }
}
