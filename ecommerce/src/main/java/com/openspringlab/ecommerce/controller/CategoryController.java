package com.openspringlab.ecommerce.controller;

import com.openspringlab.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    //GET - categories

    //GET - categories/{id}

    //POST - categories

    //PUT - categories/{id}

    //DELETE - categories/{id}
}
