package com.example.productservice.dto;

import com.example.productservice.domain.Product;
import com.example.productservice.domain.ProductOption;
import com.example.productservice.domain.enums.ProductStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

public class ProductDto {

    @Data
    public static class CreateProductDto {

        private String name;

        private Integer price;

        private Integer consumerPrice; // 소비자가

        private Float discountedRate; // 할인율

        private Boolean isSold; // 판매 여부

        private Long brandId;

        private String categoryId;

        // 상품 옵션
        private List<ProductOptionDto> productOptions;

    }

    @Data
    @Builder
    public static class ProductOptionDto {

        private String name;

        private Integer addPrice;

        private Integer stock;

        public static ProductOptionDto of(ProductOption productOption) {
            return ProductOptionDto.builder()
                .name(productOption.getName())
                .addPrice(productOption.getAddPrice())
                .stock(productOption.getStock())
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

        private Float discountedRate; // 할인율

        private String productStatus;

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
                .discountedRate(product.getDiscountedRate())
                .productStatus(product.getStatus().getDescription())
                .brandId(product.getBrandId())
                .totalStockQuantity(product.getTotalStock())
                .categoryId(product.getCategory().getCategoryId())
                .productOptions(CollectionUtils.isEmpty(product.getProductOptions()) ? null :
                    product.getProductOptions().stream()
                        .map(ProductOptionDto::of)
                        .collect(Collectors.toList()))
                .build();
        }

    }

    @Data
    public static class UpdateProductDto {

        private Long productId;

        private String name;

        private Integer price;

        private Integer consumerPrice; // 소비자가

        private Float discountedRate; // 할인율

        private ProductStatus status;

        private String categoryId;

        // 상품 옵션
        private List<UpdateProductOptionDto> productOptions;

    }

    @Data
    public static class UpdateProductOptionDto {

        private Long productOptionId;

        private String name;

        private Integer addPrice;

        private Integer stockQuantity;

    }

}
