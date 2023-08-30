package com.example.orderservice.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {
    DELIVERY_PREPARING("배송준비중"),
    DELIVERY_STARTED("배송시작"),
    DELIVERY_IN_PROGRESS("배송중"),
    DELIVERY_COMPLETED("배송완료"),
    DELIVERY_ON_HOLD("배송보류");

    private final String description;
}
