package com.example.url_shortener.service;

import com.example.url_shortener.dto.UrlRequestDto;
import com.example.url_shortener.dto.UrlResponseDto;
import com.example.url_shortener.exception.InvalidApiKeyException;
import com.example.url_shortener.model.Account;
import com.example.url_shortener.model.Url;
import com.example.url_shortener.repository.AccountRepository;
import com.example.url_shortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final AccountRepository accountRepository;

    public UrlService(UrlRepository urlRepository, AccountRepository accountRepository) {
        this.urlRepository = urlRepository;
        this.accountRepository = accountRepository;
    }

    public UrlResponseDto createShortUrl(UrlRequestDto request, String apiKey) {
        log.info("Received request to shorten URL: {}", request.originalUrl());
        Account account = accountRepository.findByApiKey(apiKey)
                .orElseThrow(() -> { log.warn("Invalid API key provided: {}", apiKey);
                    return new InvalidApiKeyException("Invalid API key");
                });


        String shortCode = generateUniqueCode();
        log.debug("Generated uniqe short code '{}' for URL '{}'", shortCode, request.originalUrl());

        Url url = new Url();
        url.setOriginalUrl(request.originalUrl());
        url.setShortCode(shortCode);
        url.setAccount(account);

        urlRepository.save(url);

        log.info("Short URL successfully created for account '{}' -> {}", account.getAccountId(), shortCode);

        String shortUrl = "http://localhost:8080/r/" + shortCode;

        return new UrlResponseDto(
                url.getOriginalUrl(),
                shortUrl,
                url.getRedirectCount()
        );
    }
    private String generateUniqueCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String code;
        Random random = new Random();

        do {
            StringBuilder sb = new StringBuilder();
            for (int i= 0; i < 6; i++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            code = sb.toString();
        }
        while (urlRepository.existsByShortCode(code));

        log.debug("Generated unique short code: {}", code);

        return code;
    }
    public Optional<Url> findByShortCode(String shortCode) {

        log.debug("Looking up URL by short code: {}", shortCode);

        Optional<Url> url = urlRepository.findByShortCode(shortCode);

        if (url.isEmpty()) {

            log.warn("No URL found for short code '{}'", shortCode);
        }
        return url;
    }

    public void incrementRedirectCount(Url url) {
        url.setRedirectCount(url.getRedirectCount() +1);
        urlRepository.save(url);

        log.info("Redirect count incremented for '{}' -> total redirects: {}",url.getShortCode(), url.getRedirectCount());
    }

    public Map<String, Long> getStatistics(String apiKey) {

        log.info("Fetching statistics for API key: {}", apiKey);

        Account account = accountRepository.findByApiKey(apiKey)
                .orElseThrow(() -> {

                    log.warn("Statistics request for invalid API key: {}", apiKey);

                    return new InvalidApiKeyException("Invalid API key");
                });

        List<Url> urls = urlRepository.findAllByAccount(account);

        log.debug("Account '{}' has URLs {} registred", account.getAccountId(), urls.size());

        Map<String, Long> stats = urls.stream()
                .collect(Collectors.toMap(Url::getOriginalUrl, Url::getRedirectCount));

        log.info("Statistics successfully retrieved for account: {}", account.getAccountId());

        return stats;
    }
}
