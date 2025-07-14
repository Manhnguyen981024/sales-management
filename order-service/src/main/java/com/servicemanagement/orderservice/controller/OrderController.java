package com.servicemanagement.orderservice.controller;

import com.servicemanagement.orderservice.dto.OrderRequest;
import com.servicemanagement.orderservice.dto.OrderResponseDTO;
import com.servicemanagement.orderservice.dto.RevenueResponse;
import com.servicemanagement.orderservice.entity.Order;
import com.servicemanagement.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequest order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
    }

    @GetMapping("/revenue/daily")
    public ResponseEntity<RevenueResponse> revenueForDaily(
            @RequestParam(name = "date", required = true) String date){
       return ResponseEntity.ok(orderService.getRevenueForDaily(date));
    }

    @GetMapping("/revenue/monthly")
    public ResponseEntity<RevenueResponse> revenueForMonthly(
            @RequestParam(name = "month", required = true) String yearMonth){
        return ResponseEntity.ok(orderService.getRevenueForMonth(yearMonth));
    }
}
