package com.example.brandservice.feign.dto;

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

        @NotNull(message = "재고수량 입력해주세요.")
        private Integer stockQuantity;

        private Long brandId;

        @NotNull(message = "카테고리를 입력해주세요.")
        private Long categoryId;

    }

    @Data
    public static class ProductResponseDto {

        private Long productId;

        private String name;

        private Integer price;

        private Integer stockQuantity;

        private Long brandId;

        private Long categoryId;

    }

}
