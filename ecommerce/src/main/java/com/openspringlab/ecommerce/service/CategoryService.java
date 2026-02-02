package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.dto.category.CategoryResponse;
import com.openspringlab.ecommerce.dto.category.CreateCategoryRequest;
import com.openspringlab.ecommerce.dto.category.UpdateCategoryRequest;
import com.openspringlab.ecommerce.exception.CategoryNotFoundException;
import com.openspringlab.ecommerce.mapper.CategoryMapper;
import com.openspringlab.ecommerce.model.Category;
import com.openspringlab.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toDto(category);
    }

    public CategoryResponse create(CreateCategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    public CategoryResponse update(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updated = categoryRepository.save(category);
        return categoryMapper.toDto(updated);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
