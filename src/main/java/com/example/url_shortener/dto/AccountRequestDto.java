package com.example.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record AccountRequestDto(
        @NotBlank(message = "Account ID cannot be blank")
        @Size(min = 3, max = 20, message = "Account ID must be between 3 and 20 characters") String accountId,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least 6 characters long") String password
) {
}
