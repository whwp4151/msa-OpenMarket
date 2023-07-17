package com.example.brandservice.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @Column(length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Embedded
    private BankInfo bankInfo;

    public static BrandAccount createBrandAccount(Brands brand, String loginId, String password, BankInfo bankInfo) {
        BrandAccount brandAccount = BrandAccount.builder()
            .brand(brand)
            .loginId(loginId)
            .password(password)
            .bankInfo(bankInfo)
            .build();

        brand.setBrandAccount(brandAccount);

        return brandAccount;
    }

    //==연관관계 메서드==//
    public void setBrand(Brands brand) {
        this.brand = brand;
    }

}
