package com.example.url_shortener.service;


import com.example.url_shortener.dto.AccountRequestDto;
import com.example.url_shortener.dto.AccountResponseDto;
import com.example.url_shortener.model.Account;
import com.example.url_shortener.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponseDto registerAccount(AccountRequestDto request) {

        log.info("Attempting to register account with id {}", request.accountId());

        if (accountRepository.existsByAccountId(request.accountId())) {

            log.warn("Registration failed - account with ID '{}' already exists", request.accountId());

            return new AccountResponseDto(false, "Account already exists", null);
        }

        String apiKey = UUID.randomUUID().toString();

        log.debug("Generated API key for new account '{}': {}", request.accountId(), apiKey);

        Account account = new Account();
        account.setAccountId(request.accountId());
        account.setPassword(request.password());
        account.setApiKey(apiKey);

        accountRepository.save(account);

        log.info("Account created successfully '{}' with API key {}", request.accountId(), apiKey);

        return new AccountResponseDto(true, "Account created successfully", apiKey);
    }
}
