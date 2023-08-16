package com.example.productservice.dto;

import com.example.productservice.domain.Product;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class ProductDto {

    @Getter
    public static class CreateProductDto {

        private String name;

        private Integer price;

        private Integer stockQuantity;

        private Long brandId;

        private String categoryId;

    }

    @Data
    @Builder
    public static class ProductResponseDto {

        private Long productId;

        private String name;

        private Integer price;

        private Long brandId;

        private String categoryId;

        public static ProductResponseDto of(Product product) {
            return ProductResponseDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .brandId(product.getBrandId())
                .categoryId(product.getCategory().getCategoryId())
                .build();
        }

    }

}
