package com.openspringlab.ecommerce.mapper;

import com.openspringlab.ecommerce.dto.order.OrderResponse;
import com.openspringlab.ecommerce.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { OrderItemMapper.class })
public interface OrderMapper {

    @Mapping(source = "orderItems", target = "items")
    OrderResponse toDto(Order order);
}
