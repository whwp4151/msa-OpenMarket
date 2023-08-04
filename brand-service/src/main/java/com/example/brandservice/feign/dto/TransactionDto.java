package com.example.brandservice.feign.dto;

import com.example.brandservice.feign.dto.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class TransactionDto {

    @Getter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionDepositRequestDto {

        private Long adminId;

        private Long brandId;

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
    @Valid
    public static class DepositDto {

        @NotNull(message = "입금 금액을 입력해주세요.")
        private Integer amount;

        @NotNull(message = "요청 받은 ID를 입력해주세요.")
        private Long transactionId;

    }

    @Getter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionCreateDto {

        private Long transactionId;

        private Long brandId;

        private Integer amount;

        private Integer previousAmount;

        private TransactionType transactionType;

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
