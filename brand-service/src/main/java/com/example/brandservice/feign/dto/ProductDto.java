package com.example.brandservice.feign.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

public class ProductDto {

    @Data
    @Valid
    public static class CreateProductDto {

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotNull(message = "가격을 입력해주세요.")
        private Integer price;

        @NotNull(message = "총 재고수량 입력해주세요.")
        private Integer totalStockQuantity;

        @NotNull(message = "소비자가격을 입력해주세요.")
        private Integer consumerPrice; // 소비자가

        @NotNull(message = "할인 가격을 입력해주세요.")
        private Integer discountedPrice; // 할인 가격

        private Boolean isSold; // 판매 여부

        private Long brandId;

        @NotNull(message = "카테고리를 입력해주세요.")
        private Long categoryId;

        // 상품 옵션
        private List<ProductOptionDto> productOptions;

    }

    @Data
    public static class ProductOptionDto {

        private String name;

        private Integer addPrice;

        private Integer stockQuantity;

    }

    @Data
    public static class ProductResponseDto {

        private Long productId;

        private String name;

        private Integer price;

        private Integer consumerPrice; // 소비자가

        private Integer discountedPrice; // 할인 가격

        private Boolean isSold; // 판매 여부

        private Long brandId;

        private Integer totalStockQuantity;

        private String categoryId;

        // 상품 옵션
        private List<ProductOptionDto> productOptions;

    }

}
