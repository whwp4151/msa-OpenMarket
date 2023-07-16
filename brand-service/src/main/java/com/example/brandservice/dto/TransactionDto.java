package com.example.brandservice.dto;

import com.example.brandservice.domain.Transaction;
import com.example.brandservice.domain.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
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
public class TransactionDto {

    private Long transactionId;

    private TransactionType transactionType;

    private Integer amount;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;

    public static TransactionDto of(Transaction transaction) {
        return TransactionDto.builder()
            .transactionId(transaction.getId())
            .transactionType(transaction.getTransactionType())
            .amount(transaction.getAmount())
            .build();
    }

}
