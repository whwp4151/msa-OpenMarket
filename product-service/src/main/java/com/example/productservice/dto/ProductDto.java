package com.example.productservice.dto;

import com.example.productservice.domain.Product;
import com.example.productservice.domain.ProductOption;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

public class ProductDto {

    @Getter
    public static class CreateProductDto {

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

    @Data
    @Builder
    public static class ProductOptionDto {

        private String name;

        private Integer addPrice;

        private Integer stockQuantity;

        public static ProductOptionDto of(ProductOption productOption) {
            return ProductOptionDto.builder()
                .name(productOption.getName())
                .addPrice(productOption.getAddPrice())
                .stockQuantity(productOption.getStockQuantity())
                .build();
        }

    }

    @Data
    @Builder
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

        public static ProductResponseDto of(Product product) {
            return ProductResponseDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .consumerPrice(product.getConsumerPrice())
                .discountedPrice(product.getDiscountedPrice())
                .isSold(product.getIsSold())
                .brandId(product.getBrandId())
                .totalStockQuantity(product.getTotalStockQuantity())
                .categoryId(product.getCategory().getCategoryId())
                .productOptions(CollectionUtils.isEmpty(product.getProductOptions()) ? null :
                    product.getProductOptions().stream()
                        .map(ProductOptionDto::of)
                        .collect(Collectors.toList()))
                .build();
        }

    }

}
