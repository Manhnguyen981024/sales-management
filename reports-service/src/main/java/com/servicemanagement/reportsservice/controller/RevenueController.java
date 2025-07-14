package com.servicemanagement.reportsservice.controller;

import com.servicemanagement.reportsservice.client.RevenueClient;
import com.servicemanagement.reportsservice.dto.RevenueResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class RevenueController {
    private final RevenueClient revenueClient;

    @GetMapping("/revenue/daily")
    public ResponseEntity<RevenueResponse> getRevenueForDaily(@RequestParam String date) {
        return ResponseEntity.ok(revenueClient.getRevenueForDaily(date));
    }

    @GetMapping("/revenue/monthly")
    public ResponseEntity<RevenueResponse> getRevenueForMonthly(@RequestParam String month) {
        return ResponseEntity.ok(revenueClient.getRevenueForMonthly(month));
    }
}
