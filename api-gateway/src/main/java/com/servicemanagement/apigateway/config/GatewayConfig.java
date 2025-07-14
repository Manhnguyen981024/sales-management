package com.servicemanagement.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Route đến auth-service
                .route("auth-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.rewritePath("/api/auth/(?<segment>.*)", "/api/auth/${segment}"))
                        .uri("lb://auth-service"))

                // Route đến product-service
                .route("product-service", r -> r.path("/api/products/**")
                        .filters(f -> f.rewritePath("/api/products/(?<segment>.*)", "/api/products/${segment}"))
                        .uri("lb://product-service"))

                // Route đến order-service
                .route("order-service", r -> r.path("/api/orders/**")
                        .filters(f -> f.rewritePath("/api/orders/(?<segment>.*)", "/api/orders/${segment}"))
                        .uri("lb://order-service"))

                // Route đến reports-service
                .route("reports-service", r -> r.path("/api/reports/**")
                        .filters(f -> f.rewritePath("/api/reports/(?<segment>.*)", "/api/reports/${segment}"))
                        .uri("lb://reports-service"))

                .build();
    }
}
