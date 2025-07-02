package com.servicemanagement.orderservice.service;

import com.servicemanagement.orderservice.dto.OrderRequest;
import com.servicemanagement.orderservice.dto.OrderResponseDTO;
import com.servicemanagement.orderservice.entity.Order;
import com.servicemanagement.orderservice.repository.OrderRepository;
import com.servicemanagement.orderservice.util.MapperUtil;
import com.servicemanagement.orderservice.util.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderResponseDTO save(OrderRequest orderRequest) {
        Order order = OrderMapper.toOrderEntity(orderRequest);
        orderRepository.save(order);

        OrderResponseDTO orderResponseDTO  = OrderMapper.toOrderResponseDTO(order);
        orderEventProducer.sendOrderEvent(MapperUtil.convertToMap(orderResponseDTO));
        return orderResponseDTO;
    }

}
