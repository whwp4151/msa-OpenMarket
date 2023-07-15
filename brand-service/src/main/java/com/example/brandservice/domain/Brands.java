package com.example.brandservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brands extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "brand_id")
    private Long id;

    private String name;

    private Integer depositAmount;

    private Long adminId;

    private Boolean isActive;

    public static Brands createBrand(String name, Integer depositAmount, Long adminId, Boolean isActive) {
        return Brands.builder()
            .name(name)
            .depositAmount(depositAmount)
            .adminId(adminId)
            .isActive(isActive)
            .build();
    }

}
