package com.example.adminservice.feign.client;

import com.example.adminservice.dto.Result;
import com.example.adminservice.feign.dto.BrandResponseDto;
import com.example.adminservice.feign.dto.TransactionDto.TransactionDepositRequestDto;
import com.example.adminservice.feign.dto.TransactionDto.TransactionResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("BRAND-SERVICE")
public interface BrandServiceClient {

    @GetMapping("/admin/brand/applications")
    Result<List<BrandResponseDto>> getBrandApplications();

    @PostMapping("/admin/brand/deposit-request")
    Result<TransactionResponseDto> depositRequest(@RequestBody TransactionDepositRequestDto dto);

    @GetMapping("/admin/brand/transactions/{id}")
    Result<List<TransactionResponseDto>> getBrandTransactions(@PathVariable("id") Long id);

}
