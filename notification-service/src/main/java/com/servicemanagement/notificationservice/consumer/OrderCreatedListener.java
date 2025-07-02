package com.servicemanagement.notificationservice.consumer;

import com.servicemanagement.notificationservice.dto.OrderResponseDTO;
import com.servicemanagement.notificationservice.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class OrderCreatedListener {
    private final EmailService emailService;

    @KafkaListener(topics = "order.created", groupId = "notification-group")
    public void listen(Map<String, Object> event) {
        log.info("📥 Nhận đơn hàng: {}", event);
        emailService.sendOrderConfirmation(event);
    }
}
