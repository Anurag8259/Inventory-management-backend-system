package com.springboot.ecommerce.inventory_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequestItemDto {
    private Long productId;
    private Integer quantity;
}
