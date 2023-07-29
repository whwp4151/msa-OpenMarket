package com.example.brandservice.dto;

import com.example.brandservice.domain.Transaction;
import com.example.brandservice.domain.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

        private TransactionType transactionType;

        private Integer amount;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime transactionDate;

        public static TransactionResponseDto of(Transaction transaction) {
            return TransactionResponseDto.builder()
                .transactionId(transaction.getId())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .build();
        }

    }

    @Data
    @Valid
    public static class DepositDto {

        @NotNull(message = "입금 금액을 입력해주세요.")
        private Integer amount;

    }

}
