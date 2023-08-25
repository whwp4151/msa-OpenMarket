package com.example.orderservice.domain;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductInfo {

    private Long productId;

    private String productName;

    private Long productOptionId;

    private String productOptionName;

    private Integer productPrice;

}
