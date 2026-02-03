package com.openspringlab.ecommerce.dto.order.item;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponse {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

}
