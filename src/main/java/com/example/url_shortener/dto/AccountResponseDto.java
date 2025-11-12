package com.example.url_shortener.dto;


public class AccountResponseDto {
    private boolean success;
    private String description;
    private String apiKey;

    public AccountResponseDto() {}

    public AccountResponseDto(boolean success, String description, String apiKey) {
        this.success = success;
        this.description = description;
        this.apiKey = apiKey;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getDescription() {
        return description;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
