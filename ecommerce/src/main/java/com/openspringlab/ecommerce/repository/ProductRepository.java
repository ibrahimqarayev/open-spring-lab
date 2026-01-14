package com.openspringlab.ecommerce.repository;

import com.openspringlab.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 1. custom query - find all products by category ID
    List<Product> findByCategoryId(Long categoryId);
}
