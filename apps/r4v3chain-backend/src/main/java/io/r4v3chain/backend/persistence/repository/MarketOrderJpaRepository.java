package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.MarketOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketOrderJpaRepository extends JpaRepository<MarketOrderEntity, String> {}
