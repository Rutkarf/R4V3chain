package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.RateLimitBucketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateLimitBucketJpaRepository extends JpaRepository<RateLimitBucketEntity, String> {
}
