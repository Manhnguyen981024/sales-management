package com.demo.productservice.controller;

import com.demo.productservice.dto.ProductDto;
import com.demo.productservice.service.ProductService;
import com.demo.productservice.validation.OnCreate;
import com.demo.productservice.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(name = "Product API", description = "Quản lý sản phẩm")
@RestController
@AllArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Tạo sản phẩm mới", description = "Tạo sản phẩm mới và trả về thông tin của sản phẩm!")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Request không hợp lệ"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @Parameter(name = "ProductDto", description = "Request body", example = "1")
    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(
            @Validated(OnCreate.class)
            @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto));
    }

    @Operation(summary = "Tra tìm sản phẩm", description = "Tra tìm thông tin sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request thành công"),
            @ApiResponse(responseCode = "400", description = "Request không hợp lệ"),
            @ApiResponse(responseCode = "500", description = "Lỗi hệ thống")
    })
    @Parameter(name = "ProductDto", description = "Request body", example = "1")
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false , defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) BigDecimal price) {
        return ResponseEntity.ok(productService.findAll(page, pageSize, sortBy, name, quantity, price));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Validated(OnUpdate.class)
            @RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productService.update(id, productDto);
        return ResponseEntity.ok(updatedProductDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(
            @PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
