package com.servicemanagement.orderservice.repository;

import com.servicemanagement.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = """
        select * from orders where cast(order_date as date) = :orderDate
    """, nativeQuery=true)
    List<Order> findByOrderDate(LocalDate orderDate);

    @Query(value = """
        select * from orders
        where to_char(order_date, 'YYYY') = :year and to_char(order_date, 'MM') = :month 
    """, nativeQuery=true)
    List<Order> findByYearAndMonth(String year, String month);

}
