package com.openspringlab.ecommerce.dto.categorydto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {

    private String name;

    private String description;

    private Boolean active;

}
