package com.openspringlab.ecommerce.controller;

import com.openspringlab.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    //GET - /orders

    //GET - /orders/{id}

    //POST - /orders

    //PUT - /orders/{id}

    //DELETE - orders/{id}

}
