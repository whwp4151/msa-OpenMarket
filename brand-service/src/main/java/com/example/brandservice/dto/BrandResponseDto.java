package com.example.brandservice.dto;

import com.example.brandservice.domain.Brands;
import io.jsonwebtoken.lang.Collections;
import java.util.List;
import java.util.stream.Collectors;
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

    private Boolean isActive;

    public static BrandResponseDto of(Brands brand) {
        return BrandResponseDto.builder()
            .brandId(brand.getId())
            .name(brand.getName())
            .depositAmount(brand.getDepositAmount())
            .adminId(brand.getAdminId())
            .isActive(brand.getIsActive())
            .build();
    }

}
