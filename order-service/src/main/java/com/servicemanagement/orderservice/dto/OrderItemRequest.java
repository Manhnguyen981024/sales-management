package com.servicemanagement.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRequest {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
