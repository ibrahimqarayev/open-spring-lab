package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.dto.product.CreateProductRequest;
import com.openspringlab.ecommerce.dto.product.ProductResponse;
import com.openspringlab.ecommerce.dto.product.UpdateProductRequest;
import com.openspringlab.ecommerce.exception.CategoryNotFoundException;
import com.openspringlab.ecommerce.exception.ProductNotFoundException;
import com.openspringlab.ecommerce.mapper.ProductMapper;
import com.openspringlab.ecommerce.model.Category;
import com.openspringlab.ecommerce.model.Product;
import com.openspringlab.ecommerce.repository.CategoryRepository;
import com.openspringlab.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        return productMapper.toDto(product);
    }

    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse create(CreateProductRequest request) {
        validateProductRequest(request.getName(),request.getPrice(),request.getSku());

        Product product = productMapper.toEntity(request);

        Optional.ofNullable(request.getCategoryId())
                .ifPresent(categoryId -> {
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() ->
                                    new CategoryNotFoundException(
                                            "Category with id " + categoryId + " not found"
                                    )
                            );
                    product.setCategory(category);
                });

        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductResponse update(Long id, UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id " + id + " not found"));

        validateProductRequest(request.getName(),request.getPrice(),request.getSku());

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setStockQuantity(request.getStockQuantity());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(
                            "Category with id " + request.getCategoryId() + " not found"));
            product.setCategory(category);
        }

        Product updatedProduct = productRepository.save(product);
        return productMapper.toDto(updatedProduct);
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id " + id + " not found"));
        productRepository.delete(product);
    }

    private void validateProductRequest(String name, BigDecimal price, String sku) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price must be non-negative");
        }
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("Product SKU is required");
        }
    }
}
