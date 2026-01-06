package com.openspringlab.ecommerce.repository;

import com.openspringlab.ecommerce.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
