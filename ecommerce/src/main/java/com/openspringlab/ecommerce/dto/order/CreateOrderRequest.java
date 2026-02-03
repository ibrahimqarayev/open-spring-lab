package com.openspringlab.ecommerce.dto.order;

import com.openspringlab.ecommerce.dto.order.item.CreateOrderItemRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {
    @NotNull(message = "items cannot be null")
    private List<CreateOrderItemRequest> items;
}