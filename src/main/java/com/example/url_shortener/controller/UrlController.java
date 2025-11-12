package com.example.url_shortener.controller;


import com.example.url_shortener.dto.UrlRequestDto;
import com.example.url_shortener.dto.UrlResponseDto;
import com.example.url_shortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/convert")
    public ResponseEntity<UrlResponseDto> createShortUrl(@Valid @RequestBody UrlRequestDto request,
                                                         @RequestHeader("X-API-KEY") String apiKey) {

        log.info("Received URL shortening request for: {} | API key: {}", request.originalUrl(), apiKey);

        UrlResponseDto response = urlService.createShortUrl(request, apiKey);

        log.info("Short URL created: {} -> {}", response.originalUrl(), response.shortUrl());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getStatistics(@RequestHeader("X-API-KEY") String apiKey) {

        log.info("Statistics request received for API key: {}", apiKey);

        Map<String,Long> stats = urlService.getStatistics(apiKey);

        log.info("Statistics retrieved: {} URLs found for this account", stats.size());
        log.debug("Statistics detail: {}", stats);

        return ResponseEntity.ok(stats);
    }

}
