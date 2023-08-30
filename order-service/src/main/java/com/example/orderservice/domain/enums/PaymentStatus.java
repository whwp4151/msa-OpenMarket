package com.example.orderservice.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    PAYMENT_COMPLETED("결제완료"),
    PAYMENT_CANCELLED("결제취소"),
    PAYMENT_FAILED("결제실패");

    private final String description;
}
