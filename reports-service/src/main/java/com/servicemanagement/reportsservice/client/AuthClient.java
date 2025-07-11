package com.servicemanagement.reportsservice.client;

import com.servicemanagement.reportsservice.dto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("api/auth/validate")
    UserInfoDto getUserInfo(@RequestParam String token);
}
