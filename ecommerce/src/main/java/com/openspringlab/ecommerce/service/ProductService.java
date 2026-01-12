package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.dto.product.CreateProductRequest;
import com.openspringlab.ecommerce.dto.product.ProductResponse;
import com.openspringlab.ecommerce.dto.product.UpdateProductRequest;
import com.openspringlab.ecommerce.model.Category;
import com.openspringlab.ecommerce.model.Product;
import com.openspringlab.ecommerce.repository.CategoryRepository;
import com.openspringlab.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    private ProductResponse mapToResponses(Product product){
        ProductResponse response = new ProductResponse();

            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
            response.setSku(product.getSku());
            response.setStockQuantity(product.getStockQuantity());

            if(product.getCategory() != null) {
                response.setCategoryId(product.getCategory().getId());
            }

            return response;

    }


    //getAll - List<ProductResponse>
    public List<ProductResponse> getAllProducts() {

        //1. Fetch the data from the DB
        List<Product> products = productRepository.findAll();

        //2. Create a empty list to hold our responses
        List<ProductResponse> responses = new ArrayList<>();

        //3. loop through each product
        for(Product product: products){
            responses.add(mapToResponses(product));
        }
        return responses;
    }

    //getById - ProductResponse
    public ProductResponse getProductById(Long id){
        // 1. Find the single product
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        //return and map directly
        return mapToResponses(product);

    }

    //getByCategoryId - List<ProductResponse>
    public List<ProductResponse> getProductsByCategoryId(Long categoryId){
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<ProductResponse> responses = new ArrayList<>();

        for(Product product: products){
            responses.add(mapToResponses(product));
        }
        return responses;
    }

    //create - ProductResponse
    public ProductResponse createProduct(CreateProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setStockQuantity(request.getStockQuantity());

        if(request.getCategoryId() != null){
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(()-> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        //Save to DB
        Product savedProduct = productRepository.save(product);

        // Map and return response
        return mapToResponses(savedProduct);
    }

    //update - ProductResponse
    public ProductResponse updateProduct(Long id, UpdateProductRequest request){

        //Hybrid method like getId and create method
        //1. Like getId - find the product
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        //2. like create - map from request
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setStockQuantity(request.getStockQuantity());

        if(request.getCategoryId() != null){
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(()-> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        //3. create a new object to append results
        Product updatedProduct = productRepository.save(product);
        return mapToResponses(updatedProduct);
    }

    //deleteById - void
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

}
