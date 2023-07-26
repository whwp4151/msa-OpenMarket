package com.example.adminservice.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequestDto {

    private String name;

    private Integer depositAmount;

    private Long adminId;

    private Boolean isActive;

}
