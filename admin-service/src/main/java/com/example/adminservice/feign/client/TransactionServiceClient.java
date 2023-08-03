package com.example.adminservice.feign.client;

import com.example.adminservice.dto.Result;
import com.example.adminservice.feign.dto.TransactionDto.TransactionResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("TRANSACTION-SERVICE")
public interface TransactionServiceClient {

    @GetMapping("/transaction/{id}")
    Result<List<TransactionResponseDto>> getTransactions(@PathVariable("id") Long id);

}
