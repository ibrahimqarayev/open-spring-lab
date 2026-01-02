package com.openspringlab.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    private String name;

    private String description;

    private BigDecimal price;

    private String sku;

    private Integer stockQuantity;

    private Boolean active;

}
