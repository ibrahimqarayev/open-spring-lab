package com.openspringlab.ecommerce.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING("PENDING"),
    PAID("PAID"),
    DELIVERED("DELIVERED");

    private final String orderStatus;
}
