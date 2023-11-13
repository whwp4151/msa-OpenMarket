package com.example.orderservice.infra;

import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderQueryRepository orderQueryRepository;

    public Order findOrderByIdWithItems(Long orderId) {
        return orderQueryRepository.findOrderByIdWithItems(orderId);
    }

    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

}
