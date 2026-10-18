package io.r4v3chain.backend.auth.security;

public interface RateLimitCounterStore {

    int incrementAndGet(String bucketKey, long windowMs);
}
