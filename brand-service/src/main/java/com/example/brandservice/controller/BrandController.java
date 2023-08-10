package com.example.brandservice.controller;

import com.example.brandservice.domain.BrandAccount;
import com.example.brandservice.dto.BrandRequestDto;
import com.example.brandservice.dto.Result;
import com.example.brandservice.feign.dto.ProductDto.CreateProductDto;
import com.example.brandservice.feign.dto.TransactionDto.DepositDto;
import com.example.brandservice.feign.dto.TransactionDto.TransactionResponseDto;
import com.example.brandservice.service.BrandService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    /*
    * 브랜드 입점 요청
    * */
    @PostMapping("/apply")
    public ResponseEntity<Result> brandApply(@RequestBody @Valid BrandRequestDto brandRequestDto,
                                             @RequestHeader(value = "user_id") String userId) {
        return ResponseEntity.ok(brandService.createBrand(brandRequestDto, userId));
    }

    /*
    * 브랜드 예치금 요청 내역 조회
    * */
    @GetMapping("/deposit-request")
    public ResponseEntity<Result> getDepositRequest(@RequestHeader(value = "user_id") String userId) {
        BrandAccount brandAccount = brandService.findByLoginIdWithBrand(userId);

        if (brandAccount.getBrand() == null) {
            return ResponseEntity.ok(Result.createErrorResult("Brand Not Found"));
        }

        return ResponseEntity.ok(brandService.getDepositRequest(brandAccount.getBrand().getId()));
    }

    /*
    * 브랜드 예치금 입금
    * */
    @PostMapping("/deposit")
    public ResponseEntity<Result> deposit(@RequestBody @Valid DepositDto dto,
                                          @RequestHeader(value = "user_id") String userId) {
        return ResponseEntity.ok(brandService.deposit(dto, userId));
    }

    @PostMapping("/product")
    public ResponseEntity<Result> createProduct(@RequestBody @Valid CreateProductDto dto,
                                                @RequestHeader(value = "user_id") String userId) {
        return ResponseEntity.ok(brandService.createProduct(dto, userId));
    }

}
