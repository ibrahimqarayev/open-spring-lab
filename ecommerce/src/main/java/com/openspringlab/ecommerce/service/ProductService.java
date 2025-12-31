package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.exception.ProductNotFoundException;
import com.openspringlab.ecommerce.model.Product;
import com.openspringlab.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}
