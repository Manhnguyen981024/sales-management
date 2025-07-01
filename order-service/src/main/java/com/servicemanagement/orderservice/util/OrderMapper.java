package com.servicemanagement.orderservice.util;

import com.servicemanagement.orderservice.dto.OrderRequest;
import com.servicemanagement.orderservice.dto.OrderResponseDTO;
import com.servicemanagement.orderservice.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderMapper {

    public static Order toOrderEntity(OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerName(orderRequest.getCustomerName())
                .totalAmount(
                        orderRequest.getOrderItems()
                        .stream()
                        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .email(orderRequest.getEmail())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .orderDate(LocalDateTime.now())
                .build();

        order.setOrderItems(
                orderRequest.getOrderItems()
                .stream()
                .map(OrderItemMapper::toOrderItemEntity)
                .toList());
        return order;
    }

    public static OrderResponseDTO toOrderResponseDTO(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(order.getId());
        orderResponseDTO.setCustomerName(order.getCustomerName());
        orderResponseDTO.setOrderDate(order.getOrderDate());
        orderResponseDTO.setEmail(order.getEmail());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setTotalPrice(order.getTotalAmount());
        orderResponseDTO.setOrderItems(
                order.getOrderItems()
                        .stream()
                        .map(OrderItemMapper::toOrderItemResponseDTO)
                        .toList()
        );
        return orderResponseDTO;
    }
}
