package com.example.productservice.domain;

import com.example.productservice.security.CustomException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
    @Index(name = "idx_product_option_product_id", columnList = "product_id")
})
public class ProductOption {

    @Id
    @GeneratedValue
    @Column(name = "product_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;

    private Integer addPrice;

    private Integer stock;

    @Builder
    public ProductOption(String name, Integer addPrice, Integer stock) {
        this.name = name;
        this.addPrice = addPrice;
        this.stock = stock;
    }

    public static ProductOption create(String name, Integer addPrice, Integer stock) {
        return ProductOption.builder()
            .name(name)
            .addPrice(addPrice)
            .stock(stock)
            .build();
    }

    public void updateOptionInfo(String name, Integer addPrice, Integer stock) {
        this.name = name;
        this.addPrice = addPrice;
        this.stock = stock;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void addStock(int quantity) {
        this.stock += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "need more stock");
        }
        this.stock = restStock;
    }

}
