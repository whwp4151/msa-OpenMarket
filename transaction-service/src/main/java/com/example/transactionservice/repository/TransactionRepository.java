package com.example.transactionservice.repository;

import com.example.transactionservice.domain.Transaction;
import com.example.transactionservice.domain.enums.TransactionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByBrandId(Long brandId);
    List<Transaction> findByBrandIdAndTransactionType(Long brandId, TransactionType transactionType);

}
