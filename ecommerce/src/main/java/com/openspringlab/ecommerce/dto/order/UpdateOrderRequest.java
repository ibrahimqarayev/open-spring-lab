package com.openspringlab.ecommerce.dto.order;

import com.openspringlab.ecommerce.dto.order.item.CreateOrderItemRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateOrderRequest {
    private List<CreateOrderItemRequest> items;
}