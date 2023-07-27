package com.example.adminservice.feign.dto;

import com.example.adminservice.feign.dto.enums.BrandStatus;
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

}
