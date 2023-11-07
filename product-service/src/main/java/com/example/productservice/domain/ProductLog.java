package com.example.productservice.domain;

import com.example.productservice.domain.enums.ProductStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
    @Index(name = "idx_product_log_product_id", columnList = "productId"),
    @Index(name = "idx_product_log_brand_id", columnList = "brandId")
})
public class ProductLog extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "product_log_id")
    private Long id;

    private Long productId;

    private String name;

    private Integer price;

    private Integer consumerPrice; // 소비자가

    private Float discountedRate; // 할인율

    private ProductStatus status;

    private Long brandId;

    // Versioning
    private Integer version;

    private String categoryId;

    @Builder
    public ProductLog(Long productId, String name, Integer price, Integer consumerPrice, Float discountedRate, ProductStatus status, Long brandId, Integer version, String categoryId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.consumerPrice = consumerPrice;
        this.discountedRate = discountedRate;
        this.status = status;
        this.brandId = brandId;
        this.version = version;
        this.categoryId = categoryId;
    }

    public static ProductLog fromProduct(Product product) {
        return ProductLog.builder()
            .productId(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .consumerPrice(product.getConsumerPrice())
            .discountedRate(product.getDiscountedRate())
            .status(product.getStatus())
            .brandId(product.getBrandId())
            .version(product.getVersion())
            .categoryId(product.getCategory().getCategoryId())
            .build();
    }

}
