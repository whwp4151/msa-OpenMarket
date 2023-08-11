package com.example.productservice.repository;

import com.example.productservice.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByBrandId(Long brandId);

}
