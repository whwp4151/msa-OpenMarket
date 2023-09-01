package com.example.orderservice.repository;

import static com.example.orderservice.domain.QDelivery.delivery;
import static com.example.orderservice.domain.QOrder.order;
import static com.example.orderservice.domain.QOrderItem.orderItem;
import static com.example.orderservice.domain.QPayment.payment;

import com.example.orderservice.domain.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderCustomRepository {

    private final JPAQueryFactory queryFactory;

    public Order findOrderByIdWithItems(Long orderId) {
        return queryFactory.select(order)
            .from(order)
            .leftJoin(order.orderItems, orderItem).fetchJoin()
            .leftJoin(order.delivery, delivery).fetchJoin()
            .leftJoin(order.payment, payment).fetchJoin()
            .where(order.id.eq(orderId))
            .distinct()
            .fetchOne();
    }

}
