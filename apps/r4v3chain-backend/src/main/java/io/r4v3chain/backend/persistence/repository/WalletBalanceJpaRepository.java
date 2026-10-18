package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.WalletBalanceEntity;
import io.r4v3chain.backend.persistence.entity.WalletBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletBalanceJpaRepository extends JpaRepository<WalletBalanceEntity, WalletBalanceId> {

    List<WalletBalanceEntity> findByIdWalletAddress(String walletAddress);
}
