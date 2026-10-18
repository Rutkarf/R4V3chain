package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.ExchangeLedgerAdjustmentEntity;
import io.r4v3chain.backend.persistence.entity.ExchangeLedgerAdjustmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeLedgerAdjustmentJpaRepository
        extends JpaRepository<ExchangeLedgerAdjustmentEntity, ExchangeLedgerAdjustmentId> {

    List<ExchangeLedgerAdjustmentEntity> findByIdWalletAddress(String walletAddress);
}
