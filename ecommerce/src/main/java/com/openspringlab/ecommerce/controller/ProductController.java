package com.openspringlab.ecommerce.controller;

import com.openspringlab.ecommerce.dto.product.CreateProductRequest;
import com.openspringlab.ecommerce.dto.product.ProductResponse;
import com.openspringlab.ecommerce.dto.product.UpdateProductRequest;
import com.openspringlab.ecommerce.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    //GET - /products
    @GetMapping
    public ResponseEntity <List<ProductResponse>> getAllProducts(){
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    //GET - /products/{id}
    @GetMapping("/{id}")
    public ResponseEntity <ProductResponse> getProductById(@PathVariable Long id){
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    //GET - /products?categoryId
    @GetMapping(params = "categoryId")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@RequestParam Long categoryId){
        List<ProductResponse> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    //POST - /products
    @PostMapping
    public ResponseEntity <ProductResponse> createProducts(@RequestBody CreateProductRequest request){
       ProductResponse product = productService.createProduct(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    //PUT - /products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request){
        ProductResponse product = productService.updateProduct(id, request);
        return ResponseEntity.ok(product);
    }
    //DELETE - /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
