package com.openspringlab.ecommerce.controller;

import com.openspringlab.ecommerce.dto.request.CreateOrderRequest;
import com.openspringlab.ecommerce.dto.response.OrderResponse;
import com.openspringlab.ecommerce.model.Order;
import com.openspringlab.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest  createOrderRequest)
    {
        OrderResponse createdOrder = orderService.createOrder(createOrderRequest);

        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/allorders")
    public ResponseEntity<List<OrderResponse>> getAllOrders()
    {
        List<OrderResponse> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id)
    {
        OrderResponse orderResponse = orderService.getOrderById(id);

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }


}
