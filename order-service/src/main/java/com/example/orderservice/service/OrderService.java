package com.example.orderservice.service;

import com.example.orderservice.domain.Address;
import com.example.orderservice.domain.Delivery;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderItem;
import com.example.orderservice.domain.Payment;
import com.example.orderservice.domain.ProductInfo;
import com.example.orderservice.domain.enums.PaymentStatus;
import com.example.orderservice.dto.OrderDto.OrderItemRequestDto;
import com.example.orderservice.dto.OrderDto.OrderRequestDto;
import com.example.orderservice.dto.OrderDto.OrderResponseDto;
import com.example.orderservice.dto.OrderDto.PaymentCompleteDto;
import com.example.orderservice.exception.CustomException;
import com.example.orderservice.repository.OrderRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Delivery delivery = Delivery.create(new Address(dto.getCity(), dto.getStreet(), dto.getZipcode()));

        Order order = Order.create(dto.getUserId(), delivery, dto.getDeliveryFee());

        for (OrderItemRequestDto item : dto.getOrderItems()) {
            OrderItem orderItem = OrderItem.create(new ProductInfo(item.getProductId(), item.getProductName(),
                item.getProductOptionId(), item.getProductOptionName(), item.getProductPrice(),
                item.getProductVersion()), item.getOrderPrice(), item.getQuantity());
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return OrderResponseDto.of(order);
    }

    @Transactional
    public OrderResponseDto paymentComplete(PaymentCompleteDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Order not found"));

        Payment payment = Payment.create(dto.getAmount(), PaymentStatus.PAYMENT_COMPLETED);
        order.setPayment(payment);

        return OrderResponseDto.of(order);
    }

}
