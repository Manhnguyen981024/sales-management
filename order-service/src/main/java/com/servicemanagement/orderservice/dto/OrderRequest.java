package com.servicemanagement.orderservice.dto;

import com.servicemanagement.orderservice.entity.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String customerName;
    private String email;
    private List<OrderItemRequest> orderItems;
}
