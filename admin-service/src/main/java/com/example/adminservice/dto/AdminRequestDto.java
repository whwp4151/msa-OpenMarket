package com.example.adminservice.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdminRequestDto {

    @NotBlank(message="아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message="비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message="이름을 입력해주세요.")
    private String name;

}
