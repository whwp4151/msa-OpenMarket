package com.example.userservice.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupDto {

    @NotBlank(message="아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message="비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message="이름을 입력해주세요.")
    private String name;

}
