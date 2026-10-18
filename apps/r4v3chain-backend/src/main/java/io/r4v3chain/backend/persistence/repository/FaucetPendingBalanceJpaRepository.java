package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.FaucetPendingBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaucetPendingBalanceJpaRepository extends JpaRepository<FaucetPendingBalanceEntity, String> {
}
