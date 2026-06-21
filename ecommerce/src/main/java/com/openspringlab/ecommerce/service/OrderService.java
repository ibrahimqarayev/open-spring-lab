package com.openspringlab.ecommerce.service;

import com.openspringlab.ecommerce.Enum.OrderStatus;
import com.openspringlab.ecommerce.dto.order.CreateOrderRequest;
import com.openspringlab.ecommerce.dto.order.OrderResponse;
import com.openspringlab.ecommerce.dto.order.UpdateOrderRequest;
import com.openspringlab.ecommerce.dto.order.item.CreateOrderItemRequest;
import com.openspringlab.ecommerce.exception.InsufficientProductStockException;
import com.openspringlab.ecommerce.exception.InvalidRequestException;
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
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
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

        if (createOrderRequest.getItems()==null || createOrderRequest.getItems().isEmpty()){
            throw new InvalidRequestException("Order must contain at least one item");
        }
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
                throw new InsufficientProductStockException("Insufficient stock for product '" + product.getName() +
                        "'. Available: " + product.getStockQuantity() + ", Requested: " + item.getQuantity()
                );
            }

            OrderItem orderItem = orderItemMapper.toEntity(item);
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice()); // Store unit price

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);
            orderItem.setTotalPrice(itemTotal);

            totalPrice = totalPrice.add(itemTotal);
            order.getOrderItems().add(orderItem);

            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
        }
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalPrice.setScale(2, RoundingMode.HALF_UP));
        Order saved = orderRepository.save(order);
        productRepository.saveAll(productMap.values());

        return orderMapper.toDto(saved);
    }

    @Transactional
    public OrderResponse update(Long id, UpdateOrderRequest updateOrderRequest){

        // 1. Validate request items are not null or empty
        if (updateOrderRequest.getItems() == null || updateOrderRequest.getItems().isEmpty()) {
            throw new InvalidRequestException("Order must contain at least one item");
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));

        // Validate order status - only allow updates for pending orders
        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new InvalidRequestException(
                    "Cannot update order in " + order.getOrderStatus() + " status. Only PENDING orders can be updated."
            );
        }

        // 3. Check for duplicate product IDs in the request
        List<Long> productIds = updateOrderRequest.getItems().stream()
                .map(CreateOrderItemRequest::getProductId)
                .toList();

        Set<Long> uniqueProductIds = new HashSet<>(productIds);
        if (uniqueProductIds.size() != productIds.size()) {
            throw new InvalidRequestException("Duplicate product IDs found in the request");
        }

        // Map existing items by product ID for easy lookup
        Map<Long, OrderItem> existingItems = order.getOrderItems().stream()
                .collect(Collectors.toMap(
                        item -> item.getProduct().getId(),
                        Function.identity()
                ));

        // Fetch products
        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // Validate all products exist
        for (CreateOrderItemRequest item : updateOrderRequest.getItems()) {
            if (!productMap.containsKey(item.getProductId())) {
                throw new ProductNotFoundException(
                        "Product with id " + item.getProductId() + " not found"
                );
            }
        }

        // Calculate stock adjustments needed
        Map<Long, Integer> stockAdjustments = new HashMap<>();

        // First, restore stock from removed items
        for (OrderItem existingItem : order.getOrderItems()) {
            Long productId = existingItem.getProduct().getId();
            if (!productIds.contains(productId)) {
                // Item is being removed - restore its stock
                stockAdjustments.merge(productId, existingItem.getQuantity(), Integer::sum);
            }
        }

        // 2. Validate stock availability considering the adjustments
        for (CreateOrderItemRequest itemRequest : updateOrderRequest.getItems()) {
            Product product = productMap.get(itemRequest.getProductId());
            OrderItem existingItem = existingItems.get(itemRequest.getProductId());

            int currentlyAllocated = existingItem != null ? existingItem.getQuantity() : 0;
            int requestedQuantity = itemRequest.getQuantity();
            int additionalNeeded = requestedQuantity - currentlyAllocated;

            // Calculate available stock (current stock + what we're returning from this order)
            int returnedStock = stockAdjustments.getOrDefault(itemRequest.getProductId(), 0);
            int availableStock = product.getStockQuantity() + returnedStock;

            if (availableStock < additionalNeeded) {
                throw new InsufficientProductStockException(
                        "Insufficient stock for product '" + product.getName() +
                                "'. Available: " + availableStock +
                                ", Additional needed: " + additionalNeeded +
                                " (Currently allocated: " + currentlyAllocated +
                                ", Requested: " + requestedQuantity + ")"
                );
            }

            // Track stock adjustment for this product
            stockAdjustments.merge(itemRequest.getProductId(), -additionalNeeded, Integer::sum);
        }

        // Remove items not in the update request and restore their stock
        Iterator<OrderItem> iterator = order.getOrderItems().iterator();
        while (iterator.hasNext()) {
            OrderItem item = iterator.next();
            if (!productIds.contains(item.getProduct().getId())) {
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                iterator.remove();
            }
        }

        BigDecimal totalPrice = BigDecimal.ZERO;

        // Update or add items
        for (CreateOrderItemRequest itemRequest : updateOrderRequest.getItems()) {
            Product product = productMap.get(itemRequest.getProductId());
            OrderItem orderItem = existingItems.get(itemRequest.getProductId());

            if (orderItem == null) {
                // New item - create and reduce stock
                orderItem = orderItemMapper.toEntity(itemRequest);
                orderItem.setOrder(order);
                orderItem.setProduct(product);
                orderItem.setPrice(product.getPrice()); // 4. Capture current price as unit price
                order.getOrderItems().add(orderItem);

                // Reduce stock for new item
                product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());

            } else {
                // Update existing item
                int oldQuantity = orderItem.getQuantity();
                int newQuantity = itemRequest.getQuantity();
                int quantityDifference = newQuantity - oldQuantity;

                // Adjust stock based on quantity change
                product.setStockQuantity(product.getStockQuantity() - quantityDifference);

                orderItem.setQuantity(newQuantity);
                // 4. Keep original unit price, don't update to current price
                // orderItem.setUnitPrice() is NOT updated - preserves original price
            }

            // Calculate total using the unit price (original or current for new items)
            BigDecimal itemTotal = orderItem.getPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);
            orderItem.setTotalPrice(itemTotal);

            totalPrice = totalPrice.add(itemTotal);
        }

        order.setTotalAmount(totalPrice.setScale(2, RoundingMode.HALF_UP));
        order.setUpdatedAt(LocalDateTime.now());

        // Save order and updated products
        Order savedOrder = orderRepository.save(order);
        productRepository.saveAll(productMap.values());

        return orderMapper.toDto(savedOrder);

    }

    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(
                        "Order with id " + id + " not found"));
        orderRepository.delete(order);
    }

}
