package com.servicemanagement.orderservice.util;

import com.servicemanagement.orderservice.dto.OrderItemRequest;
import com.servicemanagement.orderservice.dto.OrderItemResponseDTO;
import com.servicemanagement.orderservice.dto.OrderResponseDTO;
import com.servicemanagement.orderservice.entity.Order;
import com.servicemanagement.orderservice.entity.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItemMapper {

    public static OrderItem toOrderItemEntity(OrderItemRequest orderItemRequest) {
        return OrderItem.builder()
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .productName(orderItemRequest.getProductName())
                .createdAt(LocalDateTime.now())
                .totalPrice(orderItemRequest.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity())))
                .build();
    }

    public static OrderItemResponseDTO toOrderItemResponseDTO(OrderItem orderItem) {
        OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
        orderItemResponseDTO.setPrice(orderItem.getPrice());
        orderItemResponseDTO.setQuantity(orderItem.getQuantity());
        orderItemResponseDTO.setProductName(orderItem.getProductName());
        orderItemResponseDTO.setTotalPrice(orderItem.getTotalPrice());
        return orderItemResponseDTO;
    }

}
