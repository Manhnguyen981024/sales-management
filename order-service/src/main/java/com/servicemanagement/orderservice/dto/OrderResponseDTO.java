package com.servicemanagement.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private String customerName;
    private String email;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemResponseDTO> orderItems;
}
