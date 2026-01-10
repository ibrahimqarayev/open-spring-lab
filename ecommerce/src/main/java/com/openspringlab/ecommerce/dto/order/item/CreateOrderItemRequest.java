package com.openspringlab.ecommerce.dto.order.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderItemRequest {
    private Long productId;
    private int quantity;
}
