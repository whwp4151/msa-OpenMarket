package com.example.transactionservice.domain;

import com.example.transactionservice.domain.enums.TransactionType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long brandId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Integer amount;

    private LocalDateTime transactionDate;

    @Builder
    public Transaction(Long brandId, Integer amount, TransactionType transactionType) {
        this.brandId = brandId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    public static Transaction create(Long brandId, Integer amount, TransactionType transactionType) {
        return Transaction.builder()
            .brandId(brandId)
            .amount(amount)
            .transactionType(transactionType)
            .build();
    }

}
