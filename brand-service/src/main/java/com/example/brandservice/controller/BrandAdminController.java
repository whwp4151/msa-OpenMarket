package com.example.brandservice.controller;

import com.example.brandservice.dto.BrandResponseDto;
import com.example.brandservice.dto.Result;
import com.example.brandservice.dto.TransactionDto.TransactionDepositRequestDto;
import com.example.brandservice.dto.TransactionDto.TransactionResponseDto;
import com.example.brandservice.service.BrandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BrandAdminController {

    private final BrandService brandService;

    @GetMapping("/brand/applications")
    public ResponseEntity<Result> getBrandApplications() {
        List<BrandResponseDto> result = brandService.getBrandApplications();
        return ResponseEntity.ok(Result.createSuccessResult(result));
    }

    @PostMapping("/brand/deposit-request")
    public ResponseEntity<Result> depositRequest(@RequestBody TransactionDepositRequestDto dto) {
        TransactionResponseDto result = brandService.depositRequest(dto);
        return ResponseEntity.ok(Result.createSuccessResult(result));
    }

    @GetMapping("/brand/transactions/{id}")
    public ResponseEntity<Result> getBrandTransactions(@PathVariable("id") Long id) {
        List<TransactionResponseDto> result = brandService.getBrandTransactions(id);
        return ResponseEntity.ok(Result.createSuccessResult(result));
    }

}
