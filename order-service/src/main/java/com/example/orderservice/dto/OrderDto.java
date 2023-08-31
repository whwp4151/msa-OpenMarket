package com.example.orderservice.dto;

import com.example.orderservice.domain.Order;
import java.util.List;
import lombok.Builder;
import lombok.Data;

public class OrderDto {

    @Data
    public static class OrderRequestDto {
        private Long userId;
        private Integer deliveryFee;
        private String city;
        private String street;
        private String zipcode;
        private List<OrderItemRequestDto> orderItems;
    }

    @Data
    public static class OrderItemRequestDto {
        private Long productId;
        private String productName;
        private Long productOptionId;
        private String productOptionName;
        private Integer productPrice;
        private Integer productVersion;
        private Integer orderPrice;
        private Integer quantity;
    }

    @Data
    public static class PaymentCompleteDto {
        private Long orderId;
        private Integer amount;
    }

    @Data
    @Builder
    public static class OrderResponseDto {

        public static OrderResponseDto of(Order order) {
            return OrderResponseDto.builder()
                .build();
        }
    }

}
