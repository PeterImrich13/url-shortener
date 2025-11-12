package com.example.url_shortener.repository;

import com.example.url_shortener.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountId(String accountId);
    boolean existsByAccountId(String accountId);
    Optional<Account> findByApiKey(String apiKey);
}
