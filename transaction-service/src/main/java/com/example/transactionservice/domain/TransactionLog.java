package com.example.transactionservice.domain;

import com.example.transactionservice.domain.enums.TransactionType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
    @Index(name = "idx_transaction_log_brand_id", columnList = "brand_id")
})
public class TransactionLog extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "transaction_log_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long brandId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Integer amount;

    private Integer previousAmount;

    private LocalDateTime transactionDate;

    @Builder
    public TransactionLog(Long brandId, Integer amount, Integer previousAmount, TransactionType transactionType) {
        this.brandId = brandId;
        this.amount = amount;
        this.previousAmount = previousAmount;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    public static TransactionLog create(Long brandId, Integer amount, Integer previousAmount, TransactionType transactionType) {
        return TransactionLog.builder()
            .brandId(brandId)
            .amount(amount)
            .previousAmount(previousAmount)
            .transactionType(transactionType)
            .build();
    }

}
