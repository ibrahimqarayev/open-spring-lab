package com.openspringlab.ecommerce.service;

import java.util.List;

import com.openspringlab.ecommerce.model.Category;

public interface CategoryService {

    Category createCategory(Category category);

    List<Category> getActiveCategories();
}
