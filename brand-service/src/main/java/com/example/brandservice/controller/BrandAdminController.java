package com.example.brandservice.controller;

import com.example.brandservice.dto.BrandAccountDto;
import com.example.brandservice.dto.BrandAccountRequestDto;
import com.example.brandservice.dto.BrandRequestDto;
import com.example.brandservice.dto.BrandResponseDto;
import com.example.brandservice.dto.Result;
import com.example.brandservice.service.BrandService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BrandAdminController {

    private final BrandService brandService;

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
