package com.example.productservice.message.dto;

import lombok.Data;

public class OrderDto {

    @Data
    public static class PaymentCompleteDto {
        private Long orderId;
        private Integer amount;
    }

}
