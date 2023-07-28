package com.example.brandservice.controller;

import com.example.brandservice.dto.BrandRequestDto;
import com.example.brandservice.dto.BrandResponseDto;
import com.example.brandservice.dto.Result;
import com.example.brandservice.service.BrandService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/brand/apply")
    public ResponseEntity<Result> brandApply(@RequestBody @Valid BrandRequestDto brandRequestDto,
                                             @RequestHeader(value = "user_id") String userId) {
        BrandResponseDto dto = brandService.createBrand(brandRequestDto, userId);
        return ResponseEntity.ok(Result.createSuccessResult(dto));
    }

}
