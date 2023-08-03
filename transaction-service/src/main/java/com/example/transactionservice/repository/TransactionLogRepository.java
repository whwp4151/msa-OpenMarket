package com.example.transactionservice.repository;

import com.example.transactionservice.domain.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

}
