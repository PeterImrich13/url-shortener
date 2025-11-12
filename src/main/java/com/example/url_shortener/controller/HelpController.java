package com.example.url_shortener.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelpController {

    @GetMapping("/help")
    public ResponseEntity<String> getHelp() {
        String helpMessage = """
                Url Shortener API Help
                
                Installation & Run:
                -------------------
                1. Clone the project from GitHub.
                2. Configure PostgreSQL in application-dev.properties.
                3. Run with:
                    - mvn spring-boot:run
                    OR
                    - docker-compose up --build
                  
                Usage: 
                ------
                1. Register account: 
                   POST/api/register
                   {
                    "accountId:" "user123",
                    "password:" "secret123"
                   }
                  
                2. Create short URL:
                   POST /api/convert
                   Hader: x-API-KEY: <your_api-key>
                   {
                    "orignalUrl": "https://example.com"
                   }
                  
                3. Redirect: 
                   GET /r/{shortCode}
                  
                4. Get statistics:
                   GET /api/statistics
                   Header: X-API-KEY: <your_api_key>
                """;

        return ResponseEntity.ok(helpMessage);

    }
}
