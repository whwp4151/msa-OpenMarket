package com.example.brandservice.repository;

import com.example.brandservice.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brand, Long> {

}
