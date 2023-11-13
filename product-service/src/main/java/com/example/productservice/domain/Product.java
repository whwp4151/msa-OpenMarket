package com.example.productservice.domain;

import com.example.productservice.domain.enums.ProductStatus;
import com.example.productservice.application.ProductDto.UpdateProductOptionDto;
import com.example.productservice.security.CustomException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
    @Index(name = "idx_product_brand_id", columnList = "brandId")
})
@DynamicUpdate
public class Product extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer consumerPrice; // 소비자가

    private Float discountedRate; // 할인율

    private ProductStatus status;

    private Long brandId;

    // Versioning
    private Integer version = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> productOptions = new ArrayList<>();

    @Builder
    public Product(String name, Integer price, Integer consumerPrice, Float discountedRate, Boolean isSold, Long brandId, Category category) {
        this.name = name;
        this.price = price;
        this.consumerPrice = consumerPrice;
        this.discountedRate = discountedRate;
        this.status = isSold == null || !isSold ? ProductStatus.PENDING : ProductStatus.ACTIVE;
        this.brandId = brandId;
        this.category = category;
    }

    public static Product create(String name, Integer price, Integer consumerPrice, Float discountedRate, Boolean isSold, Long brandId, Category category) {
        return Product.builder()
            .name(name)
            .price(price)
            .consumerPrice(consumerPrice)
            .discountedRate(discountedRate)
            .isSold(isSold)
            .brandId(brandId)
            .category(category)
            .build();
    }

    public void updateProductInfo(String name, Integer price, Integer consumerPrice, Float discountedRate, ProductStatus status, Category category, List<UpdateProductOptionDto> productOptions) {
        this.name = name;
        this.price = price;
        this.consumerPrice = consumerPrice;
        this.discountedRate = discountedRate;
        this.status = status;
        this.category = category;
        this.version++;

        if (!CollectionUtils.isEmpty(productOptions)) {
            for (UpdateProductOptionDto optionDto : productOptions) {
                ProductOption option;

                if (optionDto.getProductOptionId() != null) {
                    option = findOptionById(optionDto.getProductOptionId());
                    option.updateOptionInfo(optionDto.getName(), optionDto.getAddPrice(), optionDto.getStockQuantity());
                } else {
                    option = ProductOption.create(optionDto.getName(), optionDto.getAddPrice(), optionDto.getStockQuantity());
                }

                this.addProductOption(option);
            }
        }
    }

    public void addProductOption(ProductOption productOption) {
        this.productOptions.add(productOption);
        productOption.setProduct(this);
    }

    private ProductOption findOptionById(Long optionId) {
        return productOptions.stream()
            .filter(option -> option.getId().equals(optionId))
            .findFirst()
            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Product option not found"));
    }

    public int getTotalStock() {
        return productOptions.stream().mapToInt(ProductOption::getStock).sum();
    }

}
