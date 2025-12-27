package com.openspringlab.ecommerce.controller;

import com.openspringlab.ecommerce.model.Product;
import com.openspringlab.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        Product savedProduct = productService.saveProduct(product);

        return new  ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


}
