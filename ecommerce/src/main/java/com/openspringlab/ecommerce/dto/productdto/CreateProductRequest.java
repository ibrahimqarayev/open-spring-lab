package com.openspringlab.ecommerce.dto.productdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    private String name;

    private String description;

    private BigDecimal price;

    private String sku;

    private Integer stockQuantity;

    private Boolean active;

}
