package com.demo.productservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long productId) {
        super("Product has " + productId + " that does not exist.");
    }
}
