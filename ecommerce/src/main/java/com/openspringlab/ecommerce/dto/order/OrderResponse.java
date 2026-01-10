package com.openspringlab.ecommerce.dto.order;

import com.openspringlab.ecommerce.dto.order.item.OrderItemResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id ;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private List<OrderItemResponse> items;
}