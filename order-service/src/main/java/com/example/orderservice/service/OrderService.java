package com.example.orderservice.service;

import com.example.orderservice.domain.Address;
import com.example.orderservice.domain.Delivery;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderItem;
import com.example.orderservice.domain.Payment;
import com.example.orderservice.domain.ProductInfo;
import com.example.orderservice.domain.enums.OrderStatus;
import com.example.orderservice.domain.enums.PaymentStatus;
import com.example.orderservice.dto.OrderDto.OrderItemDto;
import com.example.orderservice.dto.OrderDto.OrderRequestDto;
import com.example.orderservice.dto.OrderDto.OrderResponseDto;
import com.example.orderservice.dto.OrderDto.PaymentCompleteDto;
import com.example.orderservice.exception.CustomException;
import com.example.orderservice.repository.OrderCustomRepository;
import com.example.orderservice.repository.OrderRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCustomRepository orderCustomRepository;

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
        Order order = orderCustomRepository.findOrderByIdWithItems(dto.getOrderId());

        Payment payment = Payment.create(dto.getAmount(), PaymentStatus.PAYMENT_COMPLETED);
        order.setPayment(payment);

        return OrderResponseDto.of(order);
    }

    // 배치 or api 처리
    @Transactional
    public OrderResponseDto confirmPayment(Long orderId) {
        Order order = orderCustomRepository.findOrderByIdWithItems(orderId);

        if (order.getPayment() == null || order.getPayment().getStatus() != PaymentStatus.PAYMENT_COMPLETED) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Payment must be completed");
        }

        order.confirmPayment();
        return OrderResponseDto.of(order);
    }

    @Transactional
    public OrderResponseDto preparingDelivery(Long orderId) {
        Order order = orderCustomRepository.findOrderByIdWithItems(orderId);

        if (OrderStatus.ITEM_PREPARING != order.getStatus()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Order must be item preparing");
        }

        order.preparingDelivery();
        return OrderResponseDto.of(order);
    }

}
