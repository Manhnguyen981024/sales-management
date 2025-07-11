package com.servicemanagement.orderservice.service;

import com.servicemanagement.orderservice.dto.OrderRequest;
import com.servicemanagement.orderservice.dto.OrderResponseDTO;
import com.servicemanagement.orderservice.dto.RevenueResponse;
import com.servicemanagement.orderservice.entity.Order;
import com.servicemanagement.orderservice.repository.OrderRepository;
import com.servicemanagement.orderservice.util.MapperUtil;
import com.servicemanagement.orderservice.util.OrderMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
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

    public RevenueResponse getRevenueForDaily(String date) {
        RevenueResponse revenueResponse = new RevenueResponse();
        if (date == null) {
            throw new InvalidParameterException("date and month cannot be null");
        }
        try {
            LocalDate orderDate = parseDateToYYYYMMDD(date);
            List<Order> orders = orderRepository.findByOrderDate(orderDate);

            revenueResponse.setDate(orderDate.toString());
            revenueResponse.setTotalRevenue(sumTotalRevenue(orders));
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("date and month cannot be null");
        }
        return revenueResponse;
    }

    public RevenueResponse getRevenueForMonth(String yearMonth) {
        RevenueResponse revenueResponse = new RevenueResponse();
        if (yearMonth == null)
            throw new InvalidParameterException("month cannot be null");
        if (isValidMonth(yearMonth)) {
            String year =  yearMonth.split("-")[0];
            String month = yearMonth.split("-")[1];
            List<Order> orders = orderRepository.findByYearAndMonth(year, month);
            revenueResponse.setMonth(yearMonth);
            revenueResponse.setTotalRevenue(sumTotalRevenue(orders));
            return revenueResponse;
        }
        throw new InvalidParameterException("month is not valid");
    }

    private boolean isValidMonth(String month) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            YearMonth.parse(month, formatter);
            return true;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return false;
        }
    }

    private LocalDate parseDateToYYYYMMDD(String date) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private BigDecimal sumTotalRevenue(List<Order> orders) {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        return orders.stream()
                .filter(order -> order.getStatus().equals("COMPLETED"))
                .map(Order::getTotalAmount)
                .reduce(totalRevenue, BigDecimal::add);
    }
}
