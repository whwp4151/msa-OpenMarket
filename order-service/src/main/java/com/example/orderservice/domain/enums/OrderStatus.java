package com.example.orderservice.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    ORDER_RECEIVED("주문접수"),
    ORDER_CANCELLED("주문취소"),
    ITEM_PREPARING("상품준비중"),
    PURCHASE_CONFIRMED("구매확정"),
    EXCHANGE_REQUESTED("교환접수"),
    EXCHANGE_COMPLETED("교환완료"),
    REFUND_REQUESTED("환불접수"),
    REFUND_COMPLETED("환불완료");

    private final String description;
}
