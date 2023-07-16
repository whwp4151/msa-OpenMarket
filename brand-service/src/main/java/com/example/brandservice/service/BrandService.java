package com.example.brandservice.service;

import com.example.brandservice.domain.Brands;
import com.example.brandservice.dto.BrandRequestDto;
import com.example.brandservice.dto.BrandResponseDto;
import com.example.brandservice.repository.BrandsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandsRepository brandsRepository;

    public BrandResponseDto createBrand(BrandRequestDto brandRequestDto) {
        Brands brand = Brands.createBrand(brandRequestDto.getName(), brandRequestDto.getDepositAmount(), brandRequestDto.getAdminId(), brandRequestDto.getIsActive());
        brandsRepository.save(brand);

        return BrandResponseDto.of(brand);
    }

}
