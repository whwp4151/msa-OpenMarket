package com.example.orderservice.domain;

import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.domain.enums.PaymentStatus;
import com.example.orderservice.application.OrderDto.OrderItemDto;
import com.example.orderservice.application.OrderDto.OrderRequestDto;
import com.example.orderservice.application.OrderDto.OrderResponseDto;
import com.example.orderservice.application.OrderDto.PaymentCompleteDto;
import com.example.orderservice.security.CustomException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponseDto getOrder(Long orderId) {
        Order order = orderRepository.findOrderByIdWithItems(orderId);
        return OrderResponseDto.of(order);
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Delivery delivery = Delivery.create(new Address(dto.getCity(), dto.getStreet(), dto.getZipcode()));

        Order order = Order.create(dto.getUserId(), delivery, dto.getDeliveryFee());

        for (OrderItemDto item : dto.getOrderItems()) {
            OrderItem orderItem = OrderItem.create(item.toProductInfo(), item.getOrderPrice(), item.getQuantity());
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return OrderResponseDto.of(order);
    }

    @Transactional
    public OrderResponseDto paymentComplete(PaymentCompleteDto dto) {
        Order order = orderRepository.findOrderByIdWithItems(dto.getOrderId());

        Payment payment = Payment.create(dto.getAmount(), PaymentStatus.PAYMENT_COMPLETED);
        order.setPayment(payment);

        return OrderResponseDto.of(order);
    }

    // 배치 or api 처리
    @Transactional
    public OrderResponseDto confirmPayment(Long orderId) {
        Order order = orderRepository.findOrderByIdWithItems(orderId);

        if (order.getPayment() == null || order.getPayment().getStatus() != PaymentStatus.PAYMENT_COMPLETED) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Payment must be completed");
        }

        order.confirmPayment();
        return OrderResponseDto.of(order);
    }

    @Transactional
    public OrderResponseDto preparingDelivery(Long orderId) {
        Order order = orderRepository.findOrderByIdWithItems(orderId);

        if (OrderStatus.ITEM_PREPARING != order.getStatus()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Order must be item preparing");
        }

        order.preparingDelivery();
        return OrderResponseDto.of(order);
    }

}
