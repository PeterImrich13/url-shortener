package com.example.url_shortener.controller;


import com.example.url_shortener.dto.AccountRequestDto;
import com.example.url_shortener.dto.AccountResponseDto;
import com.example.url_shortener.service.AccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping("/register")
    public ResponseEntity<AccountResponseDto> registerAccount(@Valid @RequestBody AccountRequestDto request) {

        log.info("Received registration request for account ID: {}", request.accountId());

        AccountResponseDto response = accountService.registerAccount(request);

        if (response.isSuccess()) {

            log.info("Account '{}' created successfully", request.accountId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {

            log.warn("Registration failed for account '{}' : {}", request.accountId(), response.getDescription());

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
