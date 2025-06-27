package com.demo.productservice.controller;

import com.demo.productservice.dto.ProductDto;
import com.demo.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("api/")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductDto> saveProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.accepted().body(productService.save(productDto));
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductDto>> getProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false , defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) BigDecimal price) {
        return ResponseEntity.ok(productService.findAll(page, pageSize, sortBy, name, quantity, price));
    }
}
