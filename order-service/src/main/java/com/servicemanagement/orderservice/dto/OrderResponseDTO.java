package com.servicemanagement.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO implements Serializable {
    private Long orderId;
    private String customerName;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemResponseDTO> orderItems;
}
