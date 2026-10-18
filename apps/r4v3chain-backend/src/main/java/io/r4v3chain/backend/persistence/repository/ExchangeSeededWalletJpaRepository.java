package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.ExchangeSeededWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeSeededWalletJpaRepository extends JpaRepository<ExchangeSeededWalletEntity, String> {
}
