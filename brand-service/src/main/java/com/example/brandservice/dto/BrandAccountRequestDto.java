package com.example.brandservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class BrandAccountRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotNull(message = "브랜드를 입력해주세요.")
    private Long brandId;

    @NotBlank(message = "계좌번호를 입력해주세요.")
    private String accountNumber;

    @NotBlank(message = "은행이름을 입력해주세요.")
    private String bankName;

    @NotBlank(message = "예치금 예치자 이름을 입력해주세요.")
    private String holderName;

}
