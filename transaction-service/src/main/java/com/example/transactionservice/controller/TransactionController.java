package com.example.transactionservice.controller;

import com.example.transactionservice.Service.TransactionService;
import com.example.transactionservice.dto.Result;
import com.example.transactionservice.dto.TransactionDto.TransactionResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    /*
     * 브랜드 예치금 거래 내역 조회
     * */
    @GetMapping("/{id}")
    public ResponseEntity<Result> getTransactions(@PathVariable("id") Long id) {
        List<TransactionResponseDto> result = transactionService.getBrandTransactions(id);
        return ResponseEntity.ok(Result.createSuccessResult(result));
    }

    /*
     * 브랜드 예치금 요청 내역 조회
     * */
    @GetMapping("/deposit-request/{id}")
    public ResponseEntity<Result> getDepositRequest(@PathVariable("id") Long id) {
        List<TransactionResponseDto> result = transactionService.getDepositRequest(id);
        return ResponseEntity.ok(Result.createSuccessResult(result));
    }

}
