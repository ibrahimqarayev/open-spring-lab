package com.openspringlab.ecommerce.repository;

import com.openspringlab.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
