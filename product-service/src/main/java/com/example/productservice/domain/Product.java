package com.example.productservice.domain;

import com.example.productservice.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    private Integer consumerPrice; // 소비자가

    private Integer discountedPrice; // 할인 가격

    private Boolean isSold; // 판매 여부

    private Boolean isDeleted; // 삭제 여부

    private Long brandId;

    private Integer totalStockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductOption> productOptions = new ArrayList<>();

    @Builder
    public Product(String name, Integer price, Integer consumerPrice, Integer discountedPrice, Boolean isSold, Long brandId, Integer totalStockQuantity, Category category) {
        this.name = name;
        this.price = price;
        this.consumerPrice = consumerPrice;
        this.discountedPrice = discountedPrice;
        this.isSold = isSold == null ? Boolean.FALSE : isSold;
        this.isDeleted = Boolean.FALSE;
        this.brandId = brandId;
        this.totalStockQuantity = totalStockQuantity;
        this.category = category;
    }

    public static Product create(String name, Integer price, Integer consumerPrice, Integer discountedPrice, Boolean isSold, Long brandId, Integer totalStockQuantity, Category category) {
        return Product.builder()
            .name(name)
            .price(price)
            .consumerPrice(consumerPrice)
            .discountedPrice(discountedPrice)
            .isSold(isSold)
            .brandId(brandId)
            .totalStockQuantity(totalStockQuantity)
            .category(category)
            .build();
    }

    public void addProductOption(ProductOption productOption) {
        this.productOptions.add(productOption);
        productOption.setProduct(this);
    }

    public void deleteProduct() {
        this.isDeleted = Boolean.TRUE;
    }

    public void addStock(int quantity) {
        this.totalStockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.totalStockQuantity - quantity;
        if (restStock < 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "need more stock");
        }
        this.totalStockQuantity = restStock;
    }

}
