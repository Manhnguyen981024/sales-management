package com.servicemanagement.orderservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.servicemanagement.orderservice.dto.OrderResponseDTO;

import java.util.Map;

public class MapperUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> convertToMap(OrderResponseDTO dto) {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.convertValue(dto, Map.class);
    }
}
