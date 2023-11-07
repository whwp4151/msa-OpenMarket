package com.example.productservice.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    PENDING("대기중"),
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SOLD_OUT("품절");

    private final String description;
}
