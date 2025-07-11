package com.servicemanagement.reportsservice.client;

import com.servicemanagement.reportsservice.config.FeignConfig;
import com.servicemanagement.reportsservice.dto.RevenueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service", configuration = FeignConfig.class)
public interface RevenueClient {

    @GetMapping("/api/revenue/daily")
    RevenueResponse getRevenueForDaily(@RequestParam("date") String date);

    @GetMapping("/api/revenue/monthly")
    RevenueResponse getRevenueForMonthly(@RequestParam("month") String yearMonth);

}
