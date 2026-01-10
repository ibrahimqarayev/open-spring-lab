package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    //getAll - List<CategoryResponse>

    //getById - CategoryResponse

    //create - CategoryResponse

    //update - CategoryResponse

    //deleteById - void
}
