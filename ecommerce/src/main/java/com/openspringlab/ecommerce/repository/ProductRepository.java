package com.openspringlab.ecommerce.repository;

import com.openspringlab.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
