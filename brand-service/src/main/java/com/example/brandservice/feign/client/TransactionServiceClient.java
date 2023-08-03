package com.example.brandservice.feign.client;

import com.example.brandservice.dto.Result;
import com.example.brandservice.feign.dto.TransactionDto.TransactionResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("TRANSACTION-SERVICE")
public interface TransactionServiceClient {

    @GetMapping("/transaction/deposit-request/{id}")
    Result<List<TransactionResponseDto>> getDepositRequest(@PathVariable("id") Long id);

}
