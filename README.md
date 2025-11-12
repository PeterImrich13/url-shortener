# URL Shortener

A simple **Spring Boot** application for shortening URLs and tracking 
Built with **Java 21**, **Spring Boot 3**, and **PostgreSQL**. 
Fully Dockerized with GitHub Actions CI pipeline.

---

## Tech Stack

- Java 21
- Spring Boot (Web, JPA, Validation, Security)
- PostgreSQL
- Docker & Docker Compose
- GitHub Actions (CI)
- SLF4J / Logback (logging)
- Maven

---

## Features
- Users registration with auto-generated API key
- Create short URLs via REST API
- Redirect from short URL to original URL
- Get statistics (redirect counts per URL)
- Global exception handling and logging

---

## Endpoints

`POST` | `/api/register` | Register new account

`POST` | `/api/convert` | Create short URL (requires API key)

`GET` | `/r/{shortCode}` | Redirect to original URL

`GET` | `/api/statistics` | Get URL statistics (requires API key)

---

## Run with Docker Compose
```bash
docker-compose up --build

