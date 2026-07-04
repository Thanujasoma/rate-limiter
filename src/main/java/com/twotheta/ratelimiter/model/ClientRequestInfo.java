package com.twotheta.ratelimiter.model;

public class ClientRequestInfo {

    private int requestCount;
    private long windowStartTime;

    public ClientRequestInfo() {
    }

    public ClientRequestInfo(int requestCount, long windowStartTime) {
        this.requestCount = requestCount;
        this.windowStartTime = windowStartTime;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public long getWindowStartTime() {
        return windowStartTime;
    }

    public void setWindowStartTime(long windowStartTime) {
        this.windowStartTime = windowStartTime;
    }
}