package com.example.adminservice.feign.dto;

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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BankInfo {

        private String accountNumber;

        private String bankName;

        private String holderName;

    }

}
