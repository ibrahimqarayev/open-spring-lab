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

    //GET - /orders
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    //GET - /orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable(name = "id") Long orderId){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    //POST - /orders
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        return ResponseEntity.ok(orderService.createOrder(createOrderRequest));
    }

    //PUT - /orders/{id}
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrderItems(@PathVariable(name = "id") Long id,
                                                          @RequestBody UpdateOrderRequest updateOrderRequest){
        OrderResponse orderResponse = orderService.updateOrder(id, updateOrderRequest);
        return ResponseEntity.ok(orderResponse);
    }

    //DELETE - orders/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(name = "id") Long id){
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
