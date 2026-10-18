package io.r4v3chain.backend.persistence;

import io.r4v3chain.backend.auth.security.RateLimitCounterStore;
import io.r4v3chain.backend.persistence.entity.RateLimitBucketEntity;
import io.r4v3chain.backend.persistence.repository.RateLimitBucketJpaRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@ConditionalOnProperty(name = "r4v3chain.persistence.mode", havingValue = "postgres")
public class JpaRateLimitCounterStore implements RateLimitCounterStore {

    private final RateLimitBucketJpaRepository repository;

    public JpaRateLimitCounterStore(RateLimitBucketJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public int incrementAndGet(String bucketKey, long windowMs) {
        long now = System.currentTimeMillis();
        RateLimitBucketEntity entity = repository.findById(bucketKey).orElse(null);

        if (entity == null || now - entity.getWindowStartMs() >= windowMs) {
            entity = new RateLimitBucketEntity();
            entity.setBucketKey(bucketKey);
            entity.setWindowStartMs(now);
            entity.setRequestCount(1);
            entity.setUpdatedAt(Instant.now());
            repository.save(entity);
            return 1;
        }

        entity.setRequestCount(entity.getRequestCount() + 1);
        entity.setUpdatedAt(Instant.now());
        repository.save(entity);
        return entity.getRequestCount();
    }
}
