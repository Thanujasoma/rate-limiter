package com.twotheta.ratelimiter.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.twotheta.ratelimiter.model.RateLimitResponse;

@SpringBootTest
class RateLimiterServiceTest {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Test
    void shouldAllowRequestsWithinLimit() {

        String client = "alice1";

        for (int i = 1; i <= 5; i++) {

            RateLimitResponse response =
                    rateLimiterService.allow(client);

            assertTrue(response.isAllowed());
        }
    }

    @Test
    void shouldBlockWhenLimitExceeded() {

        String client = "alice2";

        for (int i = 1; i <= 5; i++) {

            rateLimiterService.allow(client);

        }

        RateLimitResponse response =
                rateLimiterService.allow(client);

        assertFalse(response.isAllowed());

        assertEquals(0,
                response.getRemainingRequests());

    }

    @Test
    void shouldMaintainSeparateLimitsForDifferentClients() {

        String alice = "alice3";
        String bob = "bob3";

        for (int i = 1; i <= 5; i++) {

            assertTrue(rateLimiterService.allow(alice).isAllowed());

            assertTrue(rateLimiterService.allow(bob).isAllowed());

        }

        assertFalse(rateLimiterService.allow(alice).isAllowed());

        assertFalse(rateLimiterService.allow(bob).isAllowed());

    }

    @Test
    void shouldResetAfterWindowExpires() throws Exception {

        String client = "alice4";

        for (int i = 1; i <= 5; i++) {

            rateLimiterService.allow(client);

        }

        assertFalse(rateLimiterService.allow(client).isAllowed());

        Thread.sleep(11000);

        RateLimitResponse response =
                rateLimiterService.allow(client);

        assertTrue(response.isAllowed());

    }

}