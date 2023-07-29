package com.example.adminservice.feign.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    REQUEST("요청"),
    DEPOSIT("입금"),
    WITHDRAWAL("출금");

    private final String value;

}
