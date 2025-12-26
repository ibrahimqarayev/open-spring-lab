package com.openspringlab.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openspringlab.ecommerce.model.Category;
import com.openspringlab.ecommerce.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> categories = new ArrayList<>();

    @Override
    public Category createCategory(Category category) {
        categories.add(category);
        return category;
    }

    @Override
    public List<Category> getActiveCategories() {
        return categories.stream()
                .filter(Category::isActive)
                .collect(Collectors.toList());
    }
}
