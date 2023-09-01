package com.example.orderservice.domain;

import com.example.orderservice.domain.enums.DeliveryStatus;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.domain.enums.PaymentStatus;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private Long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Exclude
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    @Exclude
    private Payment payment;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    @Exclude
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Integer deliveryFee;

    private Integer totalPrice;

    @Builder
    public Order(Long userId, Integer deliveryFee, Integer totalPrice) {
        this.userId = userId;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.ORDER_RECEIVED;
    }

    public static Order create(Long userId, Delivery delivery, Integer deliveryFee) {
        Order order = Order.builder()
            .userId(userId)
            .deliveryFee(deliveryFee)
            .build();

        order.setDelivery(delivery);
        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);

        this.totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        payment.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public String getOrderRepresentativeStatus() {
        if (this.delivery == null || this.payment == null) {
            return this.status.getDescription();
        }

        // 주문취소
        if (this.status == OrderStatus.ORDER_CANCELLED) {
            return this.status.getDescription();
        }

        OrderStatus orderStatus = this.status;
        PaymentStatus paymentStatus = this.payment.getStatus();
        DeliveryStatus deliveryStatus = this.delivery.getStatus();

        // 결제완료
        if (paymentStatus == PaymentStatus.PAYMENT_COMPLETED) {
            if (orderStatus == OrderStatus.ORDER_RECEIVED) {
                return paymentStatus.getDescription();
            }

            if (deliveryStatus == null) {
                return orderStatus.getDescription();
            }

            // 배송완료
            if (deliveryStatus == DeliveryStatus.DELIVERY_COMPLETED) {
                if (orderStatus == OrderStatus.ITEM_PREPARING) {
                    return deliveryStatus.getDescription();
                }

                // 구매확정, 교환, 환불
                return orderStatus.getDescription();
            }

            return deliveryStatus.getDescription();
        }

        // 결제취소, 결제실패
        return paymentStatus.getDescription();
    }

    // 결제확인
    public void confirmPayment() {
        this.status = OrderStatus.ITEM_PREPARING;
    }

}
