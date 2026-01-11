package com.openspringlab.ecommerce.mapper;


import com.openspringlab.ecommerce.dto.order.item.CreateOrderItemRequest;
import com.openspringlab.ecommerce.dto.order.item.OrderItemResponse;
import com.openspringlab.ecommerce.dto.order.item.UpdateOrderItemRequest;
import com.openspringlab.ecommerce.model.OrderItem;
import com.openspringlab.ecommerce.model.Product;
import org.hibernate.sql.Update;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemMapper {

    OrderItem toEntity(CreateOrderItemRequest createOrderItemRequest);

    OrderItem toEntity(UpdateOrderItemRequest updateOrderItemRequest);

    @Mapping(source = "product",target = "productId",qualifiedByName = "getProductId")
    OrderItemResponse toDto(OrderItem orderItem);

    @Named("getProductId")
    private Long getProductId(Product product){
        return  product.getId();
    }

}
