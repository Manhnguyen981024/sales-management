package com.demo.productservice.utils;

import com.demo.productservice.dto.ProductDto;
import com.demo.productservice.entity.Product;

public class ProductMapper {

    public static ProductDto toProductDto(Product product) {
        if (product == null)
            return null;

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getStatus(),
                product.getThumbnail()
        );
    }

    public static Product toProduct(ProductDto productDto) {
        if (productDto == null)
            return null;

        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .status(productDto.getStatus())
                .thumbnail(productDto.getThumbnail())
                .build();
    }
}
