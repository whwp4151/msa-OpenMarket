package com.example.brandservice.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
public class Brands extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "brand_id")
    private Long id;

    private String name;

    private Integer depositAmount;

    private Long adminId;

    private Boolean isActive;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_account_id")
    private BrandAccount brandAccount;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    //==생성 메서드==//
    public static Brands createBrand(String name, Integer depositAmount, Long adminId, Boolean isActive) {
        return Brands.builder()
            .name(name)
            .depositAmount(depositAmount)
            .adminId(adminId)
            .isActive(isActive)
            .build();
    }

    //==연관관계 메서드==//
    public void setBrandAccount(BrandAccount brandAccount) {
        this.brandAccount = brandAccount;
        brandAccount.setBrand(this);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setBrand(this);
    }

}
