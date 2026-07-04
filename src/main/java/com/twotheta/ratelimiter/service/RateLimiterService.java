package com.twotheta.ratelimiter.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twotheta.ratelimiter.model.ClientRequestInfo;
import com.twotheta.ratelimiter.model.RateLimitResponse;

@Service
public class RateLimiterService {

    // Maximum requests allowed
    private final int maxRequests;

    // Window size in milliseconds
    private final long windowSizeMillis;

    // Stores request information for each client
    private final ConcurrentHashMap<String, ClientRequestInfo> clientMap = new ConcurrentHashMap<>();

    public RateLimiterService(
            @Value("${rate.limit.max-requests}") int maxRequests,
            @Value("${rate.limit.window-seconds}") long windowSeconds) {

        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSeconds * 1000;
    }

    public RateLimitResponse allow(String clientId) {

        long currentTime = System.currentTimeMillis();

        synchronized (clientId.intern()) {

            ClientRequestInfo info = clientMap.get(clientId);

            // First request from this client
            if (info == null) {

                info = new ClientRequestInfo(1, currentTime);
                clientMap.put(clientId, info);

                return new RateLimitResponse(
                        true,
                        maxRequests - 1,
                        0
                );
            }

            // Check if the current window has expired
            if (currentTime - info.getWindowStartTime() >= windowSizeMillis) {

                info.setRequestCount(1);
                info.setWindowStartTime(currentTime);

                return new RateLimitResponse(
                        true,
                        maxRequests - 1,
                        0
                );
            }

            // Request is within the current window
            if (info.getRequestCount() < maxRequests) {

                info.setRequestCount(info.getRequestCount() + 1);

                return new RateLimitResponse(
                        true,
                        maxRequests - info.getRequestCount(),
                        0
                );
            }

            // Request limit exceeded
            long retryAfter =
                    (windowSizeMillis - (currentTime - info.getWindowStartTime())) / 1000;

            return new RateLimitResponse(
                    false,
                    0,
                    retryAfter
            );
        }
    }
}