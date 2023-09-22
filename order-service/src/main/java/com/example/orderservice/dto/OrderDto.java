package com.example.orderservice.dto;

import com.example.orderservice.domain.Address;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderItem;
import com.example.orderservice.domain.ProductInfo;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrderDto {

    @Data
    @Valid
    public static class OrderRequestDto {
        private Long userId;

        @NotNull
        private Integer deliveryFee;

        @NotNull
        private String city;

        @NotNull
        private String street;

        @NotNull
        private String zipcode;

        @NotNull
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

        public static OrderItemDto of(OrderItem orderItem) {
            ProductInfo productInfo = orderItem.getProductInfo();

            return OrderItemDto.builder()
                .productId(productInfo.getProductId())
                .productName(productInfo.getProductName())
                .productOptionId(productInfo.getProductOptionId())
                .productOptionName(productInfo.getProductOptionName())
                .productPrice(productInfo.getProductPrice())
                .productVersion(productInfo.getProductVersion())
                .orderPrice(orderItem.getOrderPrice())
                .quantity(orderItem.getQuantity())
                .build();
        }

        public ProductInfo toProductInfo() {
            return new ProductInfo(this.productId, this.productName,
                this.productOptionId, this.productOptionName, this.productPrice,
                this.productVersion);
        }
    }

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

        public static OrderResponseDto of(Order order) {
            Address address = order.getDelivery().getAddress();

            return OrderResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .orderStatus(order.getOrderRepresentativeStatus())
                .paymentAmount(order.getPayment() == null ? null : order.getPayment().getAmount())
                .deliveryFee(order.getDeliveryFee())
                .totalPrice(order.getTotalPrice())
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .orderItems(order.getOrderItems().stream()
                    .map(OrderItemDto::of)
                    .collect(Collectors.toList()))
                .build();
        }
    }

}
