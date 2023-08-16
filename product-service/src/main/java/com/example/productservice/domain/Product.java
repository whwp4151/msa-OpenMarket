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

    private Long brandId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductOption> productOptions = new ArrayList<>();

    @Builder
    public Product(String name, Integer price, Long brandId, Category category) {
        this.name = name;
        this.price = price;
        this.brandId = brandId;
        this.category = category;
    }

    public static Product create(String name, Integer price, Integer stockQuantity, Long brandId, Category category) {
        return Product.builder()
            .name(name)
            .price(price)
            .brandId(brandId)
            .category(category)
            .build();
    }

}
