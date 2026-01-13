package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.dto.category.CategoryResponse;
import com.openspringlab.ecommerce.dto.category.CreateCategoryRequest;
import com.openspringlab.ecommerce.dto.category.UpdateCategoryRequest;

import com.openspringlab.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//added Category model
import com.openspringlab.ecommerce.model.Category;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    //helper methods for later
    private CategoryResponse mapToResponse(Category category){
        //variables given in controller: id/name/description
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());

        return response;

    }

    //getAll - List<CategoryResponse>
    public List<CategoryResponse> getAllCategories() {

        //1. Fetch the data from the DB
        List<Category> categories = categoryRepository.findAll();

        //2. Create a empty list to hold our responses
        List<CategoryResponse> responses = new ArrayList<>();

        //3. loop through each product
        for(Category category: categories){
            responses.add(mapToResponse(category));
        }
        return responses;
    }

    //getById - CategoryResponse
    public CategoryResponse getCategoryById(Long id){
        // 1. Find the single product
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        //return and map directly
        return mapToResponse(category);

    }

    //create - CategoryResponse
    public CategoryResponse createCategory(CreateCategoryRequest request){
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category savedCategory = categoryRepository.save(category);
        return mapToResponse(savedCategory);
    }

    //update - CategoryResponse
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updated = categoryRepository.save(category);
        return mapToResponse(updated);
    }

    //deleteById - void
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }
}
