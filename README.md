# 🚦 Rate Limiter using Spring Boot

A simple and thread-safe **Fixed Window Rate Limiter** built using **Spring Boot** as part of the **TwoTheta Software Engineering Intern Take-Home Assignment**.

## 📌 Project Overview

This project implements a configurable rate limiter that restricts the number of requests a client can make within a specified time window.

Each client is identified by a unique **clientId**, and the rate limit is applied independently to every client.

Example:

- Maximum Requests: **5**
- Time Window: **10 seconds**

If a client sends more than 5 requests within 10 seconds, the subsequent requests are blocked until the current window expires.

---

## 🚀 Features

- Fixed Window Rate Limiting Algorithm
- Per-client rate limiting
- Configurable request limit and time window
- REST API implementation
- Swagger UI for API documentation
- Thread-safe implementation using `ConcurrentHashMap`
- HTTP 200 and HTTP 429 responses
- Retry-After and X-RateLimit-Remaining headers
- Unit Testing using JUnit 5

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3
- Maven
- Spring Web
- Spring Validation
- SpringDoc OpenAPI (Swagger UI)
- JUnit 5

---

## 📂 Project Structure

```
src
├── main
│   ├── java
│   │   └── com.twotheta.ratelimiter
│   │       ├── config
│   │       ├── controller
│   │       ├── model
│   │       ├── service
│   │       └── RateLimiterApplication.java
│   │
│   └── resources
│       └── application.properties
│
└── test
    └── java
        └── com.twotheta.ratelimiter.service
            └── RateLimiterServiceTest.java
```

---

## ⚙️ Configuration

Configure the rate limiter in:

`application.properties`

```properties
rate.limit.max-requests=5
rate.limit.window-seconds=10
```

---

## 📌 Algorithm Used

### Fixed Window Algorithm

Each client has:

- Request Count
- Window Start Time

Workflow:

1. First request creates a new window.
2. Requests within the current window increase the request count.
3. If the count exceeds the configured limit, the request is blocked.
4. Once the window expires, the counter resets automatically.

### Why Fixed Window?

I chose the Fixed Window algorithm because it is:

- Simple to implement
- Easy to understand
- Efficient with O(1) lookup and update operations
- Suitable for moderate traffic systems

---

## ⚖️ Trade-offs

### Advantages

- Simple implementation
- Low memory usage
- Fast request processing

### Limitations

- Allows burst traffic near window boundaries.
- Less accurate compared to Sliding Window or Token Bucket algorithms.

---

## 🌐 REST API

### Check Rate Limit

```
GET /api/rate-limit/check?clientId=alice
```

---

### Success Response

```
HTTP 200 OK
```

```json
{
  "allowed": true,
  "remainingRequests": 4,
  "retryAfter": 0
}
```

---

### Blocked Response

```
HTTP 429 Too Many Requests
```

```json
{
  "allowed": false,
  "remainingRequests": 0,
  "retryAfter": 6
}
```

---

## 📋 Response Headers

| Header | Description |
|---------|-------------|
| X-RateLimit-Remaining | Remaining requests in current window |
| Retry-After | Seconds until client can retry |

---

## 📖 Swagger UI

After running the application:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Test Cases

Implemented the following unit tests:

- Requests allowed within the limit
- Request blocked after exceeding limit
- Independent limits for multiple clients
- Window reset after expiry

All tests pass successfully.

---

## ⚠️ Edge Cases Considered

- First request from a new client
- Multiple independent clients
- Window expiration and reset
- Requests exceeding the configured limit

---

## 🔒 Concurrency

The implementation is thread-safe using:

- ConcurrentHashMap
- synchronized blocks for per-client request updates

This ensures that simultaneous requests from the same client are processed safely without race conditions.

---

## ▶️ Running the Project

Clone the repository:

```bash
git clone https://github.com/<your-username>/RateLimiter.git
```

Navigate to the project:

```bash
cd RateLimiter
```

Run:

```bash
mvn spring-boot:run
```

Or run the `RateLimiterApplication` class directly from your IDE.

---

## 👩‍💻 Author

**Thanuja Soma**

Software Engineering Intern Take-Home Assignment
