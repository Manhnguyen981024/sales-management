package com.demo.productservice.controller;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.productservice.dto.ProductDto;
import com.demo.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class MockServiceConfig {
        @Bean
        public ProductService productService() {
            return mock(ProductService.class);
        }
    }

    @Autowired
    private ProductService productService;

    @Test
    void getProducts_shouldReturnPagedResult() throws Exception {
        // ✅ Fake dữ liệu trả về từ service
        ProductDto productDto = new ProductDto(1L, "Gìay Nike", "nike",  BigDecimal.valueOf(15000000), 10, "ACTIVE", "hinh.jpg");
        List<ProductDto> productDtoList = List.of(productDto);
        Page<ProductDto> productDtoPage = new PageImpl<>(productDtoList, PageRequest.of(0,10), 1);


        // ✅ Khi gọi service thì trả về dữ liệu giả
        when(
                productService.findAll(
                        anyInt(),
                        anyInt(),
                        any(),
                        any(),
                        any(),
                        any()
                )
        ).thenReturn(productDtoPage);

        // ✅ Gọi API thật qua MockMvc
        mockMvc.perform(get("/api/products")
                    .param("page", "0")
                    .param("size", "10")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("Gìay Nike"))
                .andExpect(jsonPath("$.content[0].price").value(15000000));

    }
}
