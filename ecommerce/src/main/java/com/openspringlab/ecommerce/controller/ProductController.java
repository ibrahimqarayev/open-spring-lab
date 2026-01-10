package com.openspringlab.ecommerce.controller;

import com.openspringlab.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    //GET - /products

    //GET - /products/{id}

    //GET - /products?categoryId

    //POST - /products

    //PUT - /products/{id}

    //DELETE - /products/{id}

}
