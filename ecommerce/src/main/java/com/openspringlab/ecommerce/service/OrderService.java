package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.dto.order.CreateOrderRequest;
import com.openspringlab.ecommerce.dto.order.OrderResponse;
import com.openspringlab.ecommerce.dto.order.UpdateOrderRequest;
import com.openspringlab.ecommerce.dto.order.item.CreateOrderItemRequest;
import com.openspringlab.ecommerce.exception.OrderNotFoundException;
import com.openspringlab.ecommerce.exception.ProductNotFoundException;
import com.openspringlab.ecommerce.mapper.OrderItemMapper;
import com.openspringlab.ecommerce.mapper.OrderMapper;
import com.openspringlab.ecommerce.model.Order;
import com.openspringlab.ecommerce.model.OrderItem;
import com.openspringlab.ecommerce.model.Product;
import com.openspringlab.ecommerce.repository.OrderRepository;
import com.openspringlab.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final OrderItemMapper orderItemMapper;

    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDto).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
        return orderMapper.toDto(order);
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest createOrderRequest) {
        // Fetch all products at once (solves N+1)
        List<Long> productIds = createOrderRequest.getItems().stream()
                .map(CreateOrderItemRequest::getProductId)
                .toList();

        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // Validate all products exist
        for (CreateOrderItemRequest item : createOrderRequest.getItems()) {
            if (!productMap.containsKey(item.getProductId())) {
                throw new ProductNotFoundException(
                        "Product with id " + item.getProductId() + " not found"
                );
            }
        }

        Order order = new Order();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CreateOrderItemRequest item : createOrderRequest.getItems()) {
            Product product = productMap.get(item.getProductId());

            // Optional: Validate stock
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }

            OrderItem orderItem = orderItemMapper.toEntity(item);
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice()); // Store unit price

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            orderItem.setTotalPrice(itemTotal);

            totalPrice = totalPrice.add(itemTotal);
            order.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(totalPrice);

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse update(Long id, UpdateOrderRequest updateOrderRequest) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));

        // Map existing items by product ID for easy lookup
        Map<Long, OrderItem> existingItems = order.getOrderItems().stream()
                .collect(Collectors.toMap(
                        item -> item.getProduct().getId(),
                        Function.identity()
                ));

        // Fetch products
        List<Long> productIds = updateOrderRequest.getItems().stream()
                .map(CreateOrderItemRequest::getProductId)
                .toList();
        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // Validate products exist
        for (CreateOrderItemRequest item : updateOrderRequest.getItems()) {
            if (!productMap.containsKey(item.getProductId())) {
                throw new ProductNotFoundException(
                        "Product with id " + item.getProductId() + " not found"
                );
            }
        }

        // Remove items not in the update request
        order.getOrderItems().removeIf(
                item -> !productIds.contains(item.getProduct().getId())
        );

        BigDecimal totalPrice = BigDecimal.ZERO;

        // Update or add items
        for (CreateOrderItemRequest itemRequest : updateOrderRequest.getItems()) {
            Product product = productMap.get(itemRequest.getProductId());
            OrderItem orderItem = existingItems.get(itemRequest.getProductId());

            if (orderItem == null) {
                // New item
                orderItem = orderItemMapper.toEntity(itemRequest);
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                order.getOrderItems().add(orderItem);
            } else {
                // Update existing
                orderItem.setQuantity(itemRequest.getQuantity());
            }

            orderItem.setPrice(product.getPrice());
            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            orderItem.setTotalPrice(itemTotal);

            totalPrice = totalPrice.add(itemTotal);
        }

        order.setTotalAmount(totalPrice);

        return orderMapper.toDto(orderRepository.save(order));
    }

    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Order with id " + id + " not found"));
        orderRepository.delete(order);
    }

}
