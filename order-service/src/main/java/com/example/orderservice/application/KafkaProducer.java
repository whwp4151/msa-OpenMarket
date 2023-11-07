package com.example.orderservice.application;

import com.example.orderservice.application.OrderDto.PaymentCompleteDto;

public interface KafkaProducer {
    void paymentComplete(PaymentCompleteDto dto);
}
