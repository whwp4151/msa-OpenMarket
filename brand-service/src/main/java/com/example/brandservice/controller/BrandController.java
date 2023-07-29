package com.example.brandservice.controller;

import com.example.brandservice.domain.BrandAccount;
import com.example.brandservice.dto.BrandRequestDto;
import com.example.brandservice.dto.BrandResponseDto;
import com.example.brandservice.dto.Result;
import com.example.brandservice.dto.TransactionDto.DepositDto;
import com.example.brandservice.dto.TransactionDto.TransactionResponseDto;
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

    @PostMapping("/apply")
    public ResponseEntity<Result> brandApply(@RequestBody @Valid BrandRequestDto brandRequestDto,
                                             @RequestHeader(value = "user_id") String userId) {
        BrandAccount brandAccount = brandService.findByLoginIdWithBrand(userId);

        if (brandAccount.getBrand() != null) {
            return ResponseEntity.ok(Result.createErrorResult("Brand Already created"));
        }

        BrandResponseDto dto = brandService.createBrand(brandRequestDto, userId, brandAccount);
        return ResponseEntity.ok(Result.createSuccessResult(dto));
    }

    @GetMapping("/deposit-request")
    public ResponseEntity<Result> getDepositRequest(@RequestHeader(value = "user_id") String userId) {
        BrandAccount brandAccount = brandService.findByLoginIdWithBrand(userId);

        if (brandAccount.getBrand() == null) {
            return ResponseEntity.ok(Result.createErrorResult("Brand Not Found"));
        }

        List<TransactionResponseDto> result = brandService.getDepositRequest(userId, brandAccount.getBrand().getId());
        return ResponseEntity.ok(Result.createSuccessResult(result));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Result> deposit(@RequestBody @Valid DepositDto dto,
                                          @RequestHeader(value = "user_id") String userId) {
        TransactionResponseDto result = brandService.deposit(dto, userId);
        return ResponseEntity.ok(Result.createSuccessResult(result));
    }

}
