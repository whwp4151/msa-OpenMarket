package com.example.transactionservice.Service;

import com.example.transactionservice.domain.Transaction;
import com.example.transactionservice.domain.enums.TransactionType;
import com.example.transactionservice.dto.TransactionDto.TransactionResponseDto;
import com.example.transactionservice.repository.TransactionLogRepository;
import com.example.transactionservice.repository.TransactionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionLogRepository transactionLogRepository;

    public List<TransactionResponseDto> getBrandTransactions(Long id) {
        List<Transaction> list = transactionRepository.findByBrandId(id);

        return list.stream()
            .map(TransactionResponseDto::of)
            .collect(Collectors.toList());
    }

    public List<TransactionResponseDto> getDepositRequest(Long id) {
        List<Transaction> list = transactionRepository.findByBrandIdAndTransactionType(id, TransactionType.REQUEST);

        return list.stream()
            .map(TransactionResponseDto::of)
            .collect(Collectors.toList());
    }
}
