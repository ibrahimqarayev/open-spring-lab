package com.openspringlab.ecommerce.dto.response;

import com.openspringlab.ecommerce.Enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private Long id ;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<ProductDetailsResponse> productDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDetailsResponse
    {
        private Long productId;
        private String productName;
        private BigDecimal productPrice;
        private Integer quantity;

    }

}
