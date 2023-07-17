package com.example.brandservice.dto;

import com.example.brandservice.domain.BankInfo;
import com.example.brandservice.domain.BrandAccount;
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
public class BrandAccountDto {

    private Long brandAccountId;

    private String loginId;

    private BankInfo bankInfo;

    public static BrandAccountDto of(BrandAccount brandAccount) {
        return BrandAccountDto.builder()
            .brandAccountId(brandAccount.getId())
            .loginId(brandAccount.getLoginId())
            .bankInfo(brandAccount.getBankInfo())
            .build();
    }

}
