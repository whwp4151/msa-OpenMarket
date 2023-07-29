package com.example.brandservice.repository;

import com.example.brandservice.domain.BrandAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandAccountRepository extends JpaRepository<BrandAccount, Long> {

    boolean existsByLoginId(String loginId);

    Optional<BrandAccount> findByLoginId(String loginId);

    @Query("SELECT ba FROM BrandAccount ba join fetch ba.brand b WHERE ba.loginId = :loginId")
    Optional<BrandAccount> findByLoginIdWithBrand(@Param("loginId") String loginId);

}
