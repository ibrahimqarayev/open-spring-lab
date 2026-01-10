package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //getAll - List<ProductResponse>

    //getById - ProductResponse

    //getByCategoryId - List<ProductResponse>

    //create - ProductResponse

    //update - ProductResponse

    //deleteById - void

}
