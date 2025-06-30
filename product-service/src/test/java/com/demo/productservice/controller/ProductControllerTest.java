package com.demo.productservice.controller;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.productservice.dto.ProductDto;
import com.demo.productservice.exception.ResourceNotFoundException;
import com.demo.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void testCreateProduct_validRequest_shouldReturnCreatedProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("New Product");
        productDto.setDescription("Description here");
        productDto.setPrice(BigDecimal.valueOf(99.99));
        productDto.setQuantity(10);
        productDto.setStatus("ACTIVE");
        productDto.setThumbnail("https://example.com/thumb.jpg");

        when(productService.save(any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Product"));
    }

    @Test
    void testCreateProduct_missingName_shouldReturnBadRequest() throws Exception {
        ProductDto productDto = new ProductDto(); // thiếu name (bắt buộc theo OnCreate)

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void testUpdateProduct_validRequest_shouldReturnUpdated() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L); // bắt buộc theo OnUpdate
        productDto.setName("Updated Name");
        productDto.setPrice(BigDecimal.valueOf(120.0));

        when(productService.update(any(Long.class), any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void testUpdateProduct_nullPrice_shouldReturnBadRequest() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Missing Price");

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.price").exists());
    }

    @Test
    void testDeleteProduct_shouldReturn204() throws Exception {
        doNothing().when(productService).delete(1L);
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_nullId_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/products/null"))
                .andExpect(status().isBadRequest()); // vì "null" không convert được sang Long
    }

    @Test
    void deleteProduct_nonExistingId_shouldReturnNotFound() throws Exception {
        doThrow(new ResourceNotFoundException(1L))
                .when(productService).delete(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNotFound());
    }
}
