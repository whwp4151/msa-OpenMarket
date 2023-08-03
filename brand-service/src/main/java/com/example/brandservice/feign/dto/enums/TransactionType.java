package com.example.brandservice.feign.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    REQUEST("요청"),
    DEPOSIT("입금"),
    WITHDRAW_REQUEST("출금 요청"),
    WITHDRAWAL("출금");

    private final String value;

}
