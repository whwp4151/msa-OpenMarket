package com.example.brandservice.service;

import static java.lang.Boolean.TRUE;

import com.example.brandservice.domain.Brand;
import com.example.brandservice.repository.BrandsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BrandServiceTest {

    @Autowired
    BrandsRepository brandsRepository;

    @Test
    @DisplayName("브랜드를 생성한다.")
    public void createBrands() throws Exception {
        //given
        String brandName = "브랜드명";
        Brand brand = Brand.create(brandName, null);

        //when
        Brand saveBrand = brandsRepository.save(brand);

        //then
        assertThat(saveBrand.getName()).isEqualTo(brandName);
    }

}
