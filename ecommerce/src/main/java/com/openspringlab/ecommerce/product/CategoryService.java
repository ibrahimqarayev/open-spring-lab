package com.openspringlab.ecommerce.product;

import com.openspringlab.ecommerce.category.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository  categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category createCategory(Category category) {

        //This will validate null or blank name before DB level
        if(category.getName() == null || category.getName().isBlank())
        {
            throw new IllegalArgumentException("Category name is required");
        }

        //faster way to find duplicate name in DB
        boolean exists = categoryRepository.existsByNameIgnoreCase(category.getName());

        if(exists)
        {
            throw new RuntimeException("Category already exists");
        }

        Category savedCategory = categoryRepository.save(category);

        return savedCategory;
    }

    @Transactional(readOnly = true)
    public List<Category> getActiveCategories() {

        List<Category> categories = categoryRepository.findByActiveTrue();

        return categories;
    }
}
