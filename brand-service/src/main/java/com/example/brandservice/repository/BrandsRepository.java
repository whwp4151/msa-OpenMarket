package com.example.brandservice.repository;

import com.example.brandservice.domain.Brand;
import com.example.brandservice.domain.enums.BrandStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Long> {

    List<Brand> findByStatus(BrandStatus status);

}
