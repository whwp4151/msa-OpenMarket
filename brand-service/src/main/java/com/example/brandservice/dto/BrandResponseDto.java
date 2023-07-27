package com.example.brandservice.dto;

import com.example.brandservice.domain.Brand;
import com.example.brandservice.domain.enums.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponseDto {

    private Long brandId;

    private String name;

    private Integer depositAmount;

    private Long adminId;

    private BrandStatus status;

    public static BrandResponseDto of(Brand brand) {
        return BrandResponseDto.builder()
            .brandId(brand.getId())
            .name(brand.getName())
            .depositAmount(brand.getDepositAmount())
            .adminId(brand.getAdminId())
            .status(brand.getStatus())
            .build();
    }

}
