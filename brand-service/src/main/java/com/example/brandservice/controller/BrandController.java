package com.example.brandservice.controller;

import com.example.brandservice.dto.BrandRequestDto;
import com.example.brandservice.dto.BrandResponseDto;
import com.example.brandservice.dto.Result;
import com.example.brandservice.service.BrandService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Brand Service";
    }

    @PostMapping("/brand")
    public ResponseEntity<Result> createBrand(@RequestBody @Valid BrandRequestDto brandRequestDto) {
        BrandResponseDto dto = brandService.createBrand(brandRequestDto);
        return ResponseEntity.ok(Result.createSuccessResult(dto));
    }

}
