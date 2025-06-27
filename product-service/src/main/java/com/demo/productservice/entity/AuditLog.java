package com.demo.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditLogId;

    private String actor;

    private String action;

    private String methodName;

    @Lob
    private String arguments;

    private LocalDateTime createTime = LocalDateTime.now();
}
