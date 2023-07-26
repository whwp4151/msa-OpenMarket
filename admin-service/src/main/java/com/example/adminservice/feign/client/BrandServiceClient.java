package com.example.adminservice.feign.client;

import com.example.adminservice.dto.Result;
import com.example.adminservice.feign.dto.BrandRequestDto;
import com.example.adminservice.feign.dto.BrandResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("BRAND-SERVICE")
public interface BrandServiceClient {

    @PostMapping("/admin/brand")
    Result<BrandResponseDto> createBrand(@RequestBody BrandRequestDto brandRequestDto);

}
