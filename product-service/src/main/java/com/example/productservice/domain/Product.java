package com.example.productservice.domain;

import com.example.productservice.exception.CustomException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer stockQuantity;

    private Long brandId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Product(String name, Integer price, Integer stockQuantity, Long brandId, Category category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.brandId = brandId;
        this.category = category;
    }

    public static Product create(String name, Integer price, Integer stockQuantity, Long brandId, Category category) {
        return Product.builder()
            .name(name)
            .price(price)
            .stockQuantity(stockQuantity)
            .brandId(brandId)
            .category(category)
            .build();
    }

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "need more stock");
        }
        this.stockQuantity = restStock;
    }

}
