package com.openspringlab.ecommerce.dto.categorydto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;

}
