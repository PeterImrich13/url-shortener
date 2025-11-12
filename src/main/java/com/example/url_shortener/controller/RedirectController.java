package com.example.url_shortener.controller;

import com.example.url_shortener.model.Url;
import com.example.url_shortener.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/r")
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        log.info("Received redirect request for short code: {}", shortCode);

        Optional<Url> optionalUrl = urlService.findByShortCode(shortCode);

        if (optionalUrl.isEmpty()) {

            log.warn("No URL found for short code '{}'", shortCode);

            return ResponseEntity.notFound().build();
        }

        Url url = optionalUrl.get();
        urlService.incrementRedirectCount(url);

        log.info("Redirecting to original URL: {}", url.getOriginalUrl());
        log.debug("Short code '{}' has now {} redirects", shortCode, url.getRedirectCount());

        return ResponseEntity
                .status(302)
                .location(URI.create(url.getOriginalUrl()))
                .build();
    }
}
