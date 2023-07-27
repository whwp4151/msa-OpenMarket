package com.example.adminservice.feign.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BrandStatus {
    PENDING("입점신청"),
    APPROVED("승인"),
    REJECTED("거절"),
    INACTIVE("비활성화");

    private final String description;

}
