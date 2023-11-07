package com.example.orderservice.application;

import com.example.orderservice.application.OrderDto.OrderRequestDto;
import com.example.orderservice.application.OrderDto.OrderResponseDto;
import com.example.orderservice.application.OrderDto.PaymentCompleteDto;
import com.example.orderservice.domain.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;

    public OrderResponseDto getOrder(Long orderId) {
        return orderService.getOrder(orderId);
    }

    public OrderResponseDto createOrder(OrderRequestDto dto) {
        return orderService.createOrder(dto);
    }

    public OrderResponseDto paymentComplete(PaymentCompleteDto dto) {
        // 1. 결제완료 처리
        OrderResponseDto orderResponseDto = orderService.paymentComplete(dto);

        // 2. Product 이벤트 발행 - 재고처리
        kafkaProducer.paymentComplete(dto);

        return orderResponseDto;
    }

}
