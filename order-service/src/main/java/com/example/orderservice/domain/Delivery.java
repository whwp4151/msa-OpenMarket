package com.example.orderservice.domain;

import com.example.orderservice.domain.enums.DeliveryStatus;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Embedded
    private Address address;

    @Builder
    public Delivery(Address address) {
        this.address = address;
    }

    public static Delivery create(Address address) {
        return Delivery.builder()
            .address(address)
            .build();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    // 배송준비중
    public void preparingDelivery() {
        this.status = DeliveryStatus.DELIVERY_PREPARING;
    }

}
