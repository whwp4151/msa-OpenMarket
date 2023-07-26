package com.example.adminservice.feign.dto;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message="이름을 입력해주세요.")
    private String name;

    private Integer depositAmount;

    private Long adminId;

    private Boolean isActive;

}
