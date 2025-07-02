package com.servicemanagement.notificationservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItemResponseDTO implements Serializable {
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
