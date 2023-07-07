package com.example.userservice.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninDto {

    @NotBlank(message="아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message="비밀번호를 입력해주세요.")
    private String password;

}
