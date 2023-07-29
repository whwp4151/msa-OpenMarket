package com.example.brandservice.domain;

import com.example.brandservice.domain.enums.TransactionType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false, updatable = false)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Integer amount;

    private LocalDateTime transactionDate;

    @Builder
    public Transaction(Brand brand, Integer amount, TransactionType transactionType) {
        this.brand = brand;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    public static Transaction create(Brand brand, Integer amount, TransactionType transactionType) {
        return Transaction.builder()
            .brand(brand)
            .amount(amount)
            .transactionType(transactionType)
            .build();
    }

    //==연관관계 메서드==//
    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}
