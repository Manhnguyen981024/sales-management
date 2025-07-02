package com.servicemanagement.orderservice.service;

import com.servicemanagement.orderservice.dto.OrderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class OrderEventProducer {
    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    public void sendOrderEvent(Map<String, Object> payload) {
        kafkaTemplate.send("order.created", payload);
        log.info("✅ Sent OrderCreatedEvent to topic: order.created, orderId={}", payload);
    }

    @KafkaListener(topics = "order.created", groupId = "order-group")
    public void receiveOrderEvent(Map<String, Object> orderResponseDTO) {
        log.info("<UNK> Received event to topic: order.created {} ", orderResponseDTO);
    }
}
