package com.example.orderservice.domain;

import com.example.orderservice.domain.enums.PaymentStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    private Order order;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Builder
    public Payment(Integer amount, PaymentStatus status) {
        this.amount = amount;
        this.status = status;
    }

    public static Payment create(Integer amount, PaymentStatus status) {
        return Payment.builder()
            .amount(amount)
            .status(status)
            .build();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
