package com.openspringlab.ecommerce.dto.productdto;

import java.math.BigDecimal;
import java.time.Instant;


public class CreateProductRequest {

    private String name;

    private String description;

    private BigDecimal price;

    private String sku;

    private Integer stockQuantity;

    private Boolean active;

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public BigDecimal price() {
        return price;
    }

    public String sku() {
        return sku;
    }

    public Integer stockQuantity() {
        return stockQuantity;
    }

    public Boolean active() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CreateProductRequest() {}
}
