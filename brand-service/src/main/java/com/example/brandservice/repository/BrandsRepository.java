package com.example.brandservice.repository;

import com.example.brandservice.domain.Brand;
import com.example.brandservice.domain.enums.BrandStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Long> {

    List<Brand> findByStatus(BrandStatus status);

    @Query("SELECT distinct b FROM Brand  b"
        + " JOIN FETCH b.transactions t"
        + " WHERE b.id = :id"
        + " ORDER BY t.id DESC")
    Optional<Brand> findByIdWithTransactions(@Param("id") Long id);

}
