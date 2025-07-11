package com.servicemanagement.reportsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private String username;
    private List<String> roles;
}
