package com.demo.productservice.specification;

import com.demo.productservice.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filter(String name, Integer minQty, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty())
                predicates.add( cb.like( cb.lower(root.get("name")) , "%" + name.toLowerCase() + "%"));

            if(minQty != null && minQty > 0)
                predicates.add( cb.greaterThanOrEqualTo(root.get("quantity"), minQty));

            if(maxPrice != null && maxPrice.intValue() > 0)
                predicates.add( cb.lessThanOrEqualTo(root.get("price"), maxPrice));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
