package com.example.productservice.message.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrderDto {

    @Data
    public static class PaymentCompleteDto {
        private Long orderId;
        private Integer amount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderResponseDto {
        private Long orderId;
        private Long userId;
        private String orderStatus;
        private Integer paymentAmount;
        private Integer deliveryFee;
        private Integer totalPrice;
        private String city;
        private String street;
        private String zipcode;
        private List<OrderItemDto> orderItems;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDto {
        private Long productId;
        private String productName;
        private Long productOptionId;
        private String productOptionName;
        private Integer productPrice;
        private Integer productVersion;
        private Integer orderPrice;
        private Integer quantity;
    }

}
