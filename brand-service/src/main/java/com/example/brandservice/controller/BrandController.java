package com.example.brandservice.controller;

import com.example.brandservice.dto.BrandAccountDto;
import com.example.brandservice.dto.BrandAccountRequestDto;
import com.example.brandservice.dto.BrandRequestDto;
import com.example.brandservice.dto.BrandResponseDto;
import com.example.brandservice.dto.Result;
import com.example.brandservice.service.BrandService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Brand Service"
            + ", token secret= " + env.getProperty("brand.secret")
            + ", token expiration time=" + env.getProperty("brand.expiration_time");
    }

    @PostMapping("/brand")
    public ResponseEntity<Result> createBrand(@RequestBody @Valid BrandRequestDto brandRequestDto) {
        BrandResponseDto dto = brandService.createBrand(brandRequestDto);
        return ResponseEntity.ok(Result.createSuccessResult(dto));
    }

    @PostMapping("/brandAccount")
    public ResponseEntity<Result> createBrandAccount(@RequestBody @Valid BrandAccountRequestDto brandAccountRequestDto) {
        BrandAccountDto brandAccount = brandService.createBrandAccount(brandAccountRequestDto);
        return ResponseEntity.ok(Result.createSuccessResult(brandAccount));
    }

}
