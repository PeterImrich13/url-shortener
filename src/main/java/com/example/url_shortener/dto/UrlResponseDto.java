package com.example.url_shortener.dto;

public record UrlResponseDto(
        String originalUrl,
        String shortUrl,
        Long redirectCount
) {
}
