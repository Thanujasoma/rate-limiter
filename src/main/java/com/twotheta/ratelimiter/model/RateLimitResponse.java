package com.twotheta.ratelimiter.model;

public class RateLimitResponse {
	private boolean allowed;
    private int remainingRequests;
    private long retryAfter;

    public RateLimitResponse() {
    }

    public RateLimitResponse(boolean allowed,
                             int remainingRequests,
                             long retryAfter) {
        this.allowed = allowed;
        this.remainingRequests = remainingRequests;
        this.retryAfter = retryAfter;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public int getRemainingRequests() {
        return remainingRequests;
    }

    public void setRemainingRequests(int remainingRequests) {
        this.remainingRequests = remainingRequests;
    }

    public long getRetryAfter() {
        return retryAfter;
    }

    public void setRetryAfter(long retryAfter) {
        this.retryAfter = retryAfter;
    }

}
