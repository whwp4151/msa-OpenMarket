package com.example.orderservice.domain;

public interface OrderRepository {
    Order findOrderByIdWithItems(Long orderId);
    Order save(Order order);
}
