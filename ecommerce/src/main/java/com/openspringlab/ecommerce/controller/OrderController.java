package com.openspringlab.ecommerce.controller;

import com.openspringlab.ecommerce.dto.order.CreateOrderRequest;
import com.openspringlab.ecommerce.dto.order.OrderResponse;
import com.openspringlab.ecommerce.dto.order.UpdateOrderRequest;
import com.openspringlab.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest createOrderRequest) {
        return ResponseEntity.ok(orderService.create(createOrderRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrderItems(@PathVariable Long id,
                                                          @RequestBody UpdateOrderRequest updateOrderRequest) {
        OrderResponse orderResponse = orderService.update(id, updateOrderRequest);
        return ResponseEntity.ok(orderResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
