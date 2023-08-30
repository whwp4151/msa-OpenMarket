package com.example.orderservice.domain;

import com.example.orderservice.domain.enums.OrderStatus;
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
    public Order(Long userId) {
        this.userId = userId;
        this.status = OrderStatus.ORDER_RECEIVED;
    }

    public static Order create(Long userId, Delivery delivery, Payment payment) {
        Order order = Order.builder()
            .userId(userId)
            .build();

        order.setDelivery(delivery);
        order.setPayment(payment);
        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        payment.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}
