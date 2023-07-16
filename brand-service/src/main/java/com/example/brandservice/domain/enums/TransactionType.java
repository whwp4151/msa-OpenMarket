package com.example.brandservice.domain.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT("입금"),
    WITHDRAWAL("출금");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }
}
