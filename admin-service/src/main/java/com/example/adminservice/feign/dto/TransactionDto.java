package com.example.adminservice.feign.dto;

import com.example.adminservice.feign.dto.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;

public class TransactionDto {

    @Data
    @Valid
    public static class TransactionDepositRequestDto {

        private Long adminId;

        @NotNull(message = "브랜드 아이디를 입력해주세요.")
        private Long brandId;

        @NotNull(message = "요청 금액을 입력해주세요.")
        private Integer amount;

    }

    @Data
    public static class TransactionResponseDto {

        private Long transactionId;

        private TransactionType transactionType;

        private Integer amount;

        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
        private LocalDateTime transactionDate;

    }

}
