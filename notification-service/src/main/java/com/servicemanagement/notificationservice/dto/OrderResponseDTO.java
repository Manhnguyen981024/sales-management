package com.servicemanagement.notificationservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO implements Serializable {
    private Long orderId;
    private String customerName;
    private String email;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemResponseDTO> orderItems;
}
