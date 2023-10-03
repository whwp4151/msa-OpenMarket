package com.example.brandservice.controller;

import com.example.brandservice.annotation.Login;
import com.example.brandservice.domain.BrandAccount;
import com.example.brandservice.dto.Result;
import com.example.brandservice.feign.dto.ProductDto;
import com.example.brandservice.service.BrandService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
public class ApprovedBrandController {

    private final BrandService brandService;

    @PostMapping("/product")
    public ResponseEntity<Result> createProduct(@RequestBody @Valid ProductDto.CreateProductDto dto,
                                                @Login BrandAccount brandAccount) {
        return ResponseEntity.ok(brandService.createProduct(dto, brandAccount));
    }

    @GetMapping("/product")
    public ResponseEntity<Result> getProduct(@Login BrandAccount brandAccount) {
        return ResponseEntity.ok(brandService.getProduct(brandAccount));
    }

}
