package com.example.orderservice.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Embedded
    private ProductInfo productInfo;

    private Integer orderPrice;

    private Integer quantity;

    @Builder
    public OrderItem(ProductInfo productInfo, Integer orderPrice, Integer quantity) {
        this.productInfo = productInfo;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
    }

    public static OrderItem create(ProductInfo productInfo, Integer orderPrice, Integer quantity) {
        return OrderItem.builder()
            .productInfo(productInfo)
            .orderPrice(orderPrice)
            .quantity(quantity)
            .build();
    }

    public int getTotalPrice() {
            return orderPrice * quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
