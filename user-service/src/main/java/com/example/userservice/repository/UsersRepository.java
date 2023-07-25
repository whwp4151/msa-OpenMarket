package com.example.userservice.repository;

import com.example.userservice.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);

}
