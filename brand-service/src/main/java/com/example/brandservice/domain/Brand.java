package com.example.brandservice.domain;

import com.example.brandservice.domain.enums.BrandStatus;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "brand_id")
    private Long id;

    private String name;

    private Integer depositAmount;

    private Long adminId;

    @Enumerated(EnumType.STRING)
    private BrandStatus status; // 브랜드 상태 (입점 신청 중, 승인, 거절 등)

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_account_id")
    private BrandAccount brandAccount;

    @Builder
    public Brand(String name, BrandStatus status, BrandAccount brandAccount) {
        this.name = name;
        this.status = status;
        this.depositAmount = 0;
        this.brandAccount = brandAccount;

        brandAccount.setBrand(this);
    }

    //==생성 메서드==//
    public static Brand create(String name, BrandAccount brandAccount) {
        return Brand.builder()
            .name(name)
            .status(BrandStatus.PENDING)
            .brandAccount(brandAccount)
            .build();
    }

    public void setAdmin(Long adminId) {
        this.adminId = adminId;
    }

    public void approve() {
        this.status = BrandStatus.APPROVED;
    }

    public void deposit(Integer amount) {
        if (this.depositAmount == null) this.depositAmount = 0;

        this.depositAmount += amount;
    }

    public boolean isApproved() {
        return BrandStatus.APPROVED.equals(this.status);
    }

}
