package com.demo.productservice.service;

import com.demo.productservice.dto.ProductDto;
import com.demo.productservice.entity.Product;
import com.demo.productservice.repositoy.ProductRepository;
import com.demo.productservice.specification.ProductSpecification;
import com.demo.productservice.utils.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductDto> findAll(int page, int size, String sortBy, String name, Integer quantity, BigDecimal price) {
        Specification<Product> spec = ProductSpecification.filter(name, quantity, price);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return productRepository.findAll(spec, pageable)
                .map(ProductMapper::toProductDto);
    }

    public ProductDto save(ProductDto productDto) {
        Product newProduct = ProductMapper.toProduct(productDto);

        newProduct.setCreatedAt(LocalDateTime.now());
        productRepository.save(newProduct);

        return ProductMapper.toProductDto(newProduct);
    }
}
