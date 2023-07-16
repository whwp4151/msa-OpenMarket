package com.example.brandservice.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandAccount extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "brand_account_id")
    private Long id;

    @OneToOne(mappedBy = "brandAccount", fetch = FetchType.LAZY)
    private Brands brand;

    @Embedded
    private BankInfo bankInfo;

    //==연관관계 메서드==//
    public void setBrand(Brands brand) {
        this.brand = brand;
    }

}
