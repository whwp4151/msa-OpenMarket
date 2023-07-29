package com.example.brandservice.repository;

import com.example.brandservice.domain.Transaction;
import com.example.brandservice.domain.enums.TransactionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByBrand_IdAndTransactionType(Long brandId, TransactionType transactionType);

}
