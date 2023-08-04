package com.example.transactionservice.Service;

import com.example.transactionservice.domain.Transaction;
import com.example.transactionservice.domain.TransactionLog;
import com.example.transactionservice.domain.enums.TransactionType;
import com.example.transactionservice.dto.TransactionDto.TransactionCreateDto;
import com.example.transactionservice.dto.TransactionDto.TransactionResponseDto;
import com.example.transactionservice.repository.TransactionLogRepository;
import com.example.transactionservice.repository.TransactionRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
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

    @Transactional
    public void createTransaction(TransactionCreateDto dto) {
        TransactionType transactionType = dto.getTransactionType();
        Transaction transaction = null;

        if (TransactionType.REQUEST == transactionType || TransactionType.WITHDRAW_REQUEST == transactionType) {
            transaction = Transaction.create(dto.getBrandId(), dto.getAmount(), transactionType);
        } else if (TransactionType.DEPOSIT == transactionType || TransactionType.WITHDRAWAL == transactionType) {
            Optional<Transaction> optionalTransaction = transactionRepository.findById(dto.getTransactionId());
            if (optionalTransaction.isPresent()) {
                transaction = optionalTransaction.get();
                transaction.updateTransactionType(transactionType);
            }
        }

        if (transaction != null) {
            transactionRepository.save(transaction);
        }


        TransactionLog transactionLog = TransactionLog.create(dto.getBrandId(), dto.getAmount(), dto.getPreviousAmount(), transactionType);
        transactionLogRepository.save(transactionLog);
    }

}
