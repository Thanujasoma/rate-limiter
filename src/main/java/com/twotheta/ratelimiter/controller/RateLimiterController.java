package com.twotheta.ratelimiter.controller;

import com.twotheta.ratelimiter.model.RateLimitResponse;
import com.twotheta.ratelimiter.service.RateLimiterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rate-limit")
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Operation(
            summary = "Check Rate Limit",
            description = "Checks whether the client is allowed to make a request."
    )
    @ApiResponse(responseCode = "200", description = "Request Allowed")
    @ApiResponse(responseCode = "429", description = "Rate Limit Exceeded")
    @GetMapping("/check")
    public ResponseEntity<RateLimitResponse> checkRateLimit(

            @Parameter(description = "Unique Client ID")
            @RequestParam
            @NotBlank
            String clientId) {

        RateLimitResponse response = rateLimiterService.allow(clientId);

        HttpHeaders headers = new HttpHeaders();

        headers.add("X-RateLimit-Remaining",
                String.valueOf(response.getRemainingRequests()));

        headers.add("Retry-After",
                String.valueOf(response.getRetryAfter()));

        if (response.isAllowed()) {

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(response);

        } else {

            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .headers(headers)
                    .body(response);
        }

    }

}