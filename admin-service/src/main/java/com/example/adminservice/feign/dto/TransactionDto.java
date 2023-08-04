package com.example.adminservice.feign.dto;

import com.example.adminservice.feign.dto.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class TransactionDto {

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Valid
    public static class TransactionDepositRequestDto {

        private Long adminId;

        @NotNull(message = "브랜드 아이디를 입력해주세요.")
        private Long brandId;

        @NotNull(message = "요청 금액을 입력해주세요.")
        private Integer amount;

    }

    @Getter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionResponseDto {

        private Long transactionId;

        private Long brandId;

        private TransactionType transactionType;

        private Integer amount;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime transactionDate;

    }

    @Getter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionResult {

        private Long brandId;

        private Integer depositAmount;

        private TransactionType transactionType;

    }

}
