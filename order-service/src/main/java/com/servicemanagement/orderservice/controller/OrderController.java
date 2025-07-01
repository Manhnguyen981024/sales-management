package com.servicemanagement.orderservice.controller;

import com.servicemanagement.orderservice.dto.OrderRequest;
import com.servicemanagement.orderservice.dto.OrderResponseDTO;
import com.servicemanagement.orderservice.entity.Order;
import com.servicemanagement.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequest order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
    }
}
