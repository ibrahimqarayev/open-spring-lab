package com.openspringlab.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderRequest {

    @NotEmpty
    private Map<Long, Integer> orderInput; //<ProductId, Quantity>
}