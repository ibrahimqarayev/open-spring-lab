package com.openspringlab.ecommerce.product;

import com.openspringlab.ecommerce.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Boolean existsByNameIgnoreCase(String name);

    List<Category> findByActiveTrue();
}
