package com.example.productservice.repository;

import com.example.productservice.domain.ProductLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLogRepository extends JpaRepository<ProductLog, Long> {

}
