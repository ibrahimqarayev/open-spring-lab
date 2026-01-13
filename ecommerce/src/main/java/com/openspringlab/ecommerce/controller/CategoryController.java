package com.openspringlab.ecommerce.controller;

import com.openspringlab.ecommerce.dto.category.CategoryResponse;
import com.openspringlab.ecommerce.dto.category.CreateCategoryRequest;
import com.openspringlab.ecommerce.dto.category.UpdateCategoryRequest;
import com.openspringlab.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//manually imported things
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    //GET - categories
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        List<CategoryResponse> response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }

    //GET - categories/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id){
        CategoryResponse response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    //POST - categories
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request){
        CategoryResponse category = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    //PUT - categories/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody UpdateCategoryRequest request){
        CategoryResponse category = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(category);
    }

    //DELETE - categories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
