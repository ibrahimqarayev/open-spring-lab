package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.dto.request.CreateOrderRequest;
import com.openspringlab.ecommerce.dto.response.OrderResponse;
import com.openspringlab.ecommerce.exception.OrderNotFoundException;
import com.openspringlab.ecommerce.model.Order;
import com.openspringlab.ecommerce.model.Product;
import com.openspringlab.ecommerce.repository.OrderRepository;
import com.openspringlab.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {

        Map<Long, Integer> orderInput = createOrderRequest.getOrderInput();

        Set<Product> products = productRepository.findAllById(orderInput.keySet())
                .stream()
                .collect(Collectors.toSet());

        BigDecimal totalAmount = products.stream()
                .map(product ->
                        product.getPrice()
                                .multiply(BigDecimal.valueOf(orderInput.get(product.getId())))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Order order = new Order();

        order.setTotalAmount(totalAmount);

        order.setProducts(products);

        orderRepository.save(order);

        List<OrderResponse.ProductDetailsResponse> productDetails =
                products.stream()
                        .map(product -> OrderResponse.ProductDetailsResponse.builder()
                                        .productId(product.getId())
                                        .productName(product.getName())
                                        .productPrice(product.getPrice())
                                        .quantity(orderInput.get(product.getId()))
                                        .build()

                        ).toList();

        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(totalAmount)
                .status(order.getStatus())
                .productDetails(productDetails).build();
    }

    public List<OrderResponse> getAllOrders()
    {
        List<Order> extractOrders = orderRepository.findAll();

        List<OrderResponse> orderResponseList = new ArrayList<>();

        for(Order order : extractOrders)
        {
            OrderResponse orderResponse = OrderResponse.builder().
                    id(order.getId())
                    .orderDate(order.getOrderDate())
                    .totalAmount(order.getTotalAmount())
                    .status(order.getStatus())
                    .build();

            orderResponseList.add(orderResponse);
        }

        return orderResponseList;

    }

    public OrderResponse getOrderById(Long id)
    {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order Not Found")
        );

        return OrderResponse.builder().
                id(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();

    }

}
