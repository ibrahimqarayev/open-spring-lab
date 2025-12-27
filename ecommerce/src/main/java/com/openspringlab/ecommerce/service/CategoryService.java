package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.exception.CategoryAlreadyExistException;
import com.openspringlab.ecommerce.model.Category;
import com.openspringlab.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new CategoryAlreadyExistException("Category already exists");
        }
        return categoryRepository.save(category);
    }
}
