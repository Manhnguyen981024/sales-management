package com.servicemanagement.orderservice.client;

import com.servicemanagement.orderservice.dto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthClient {
    @GetMapping("/api/auth/validate")
    UserInfoDto validateToken(@RequestParam String token);
}
