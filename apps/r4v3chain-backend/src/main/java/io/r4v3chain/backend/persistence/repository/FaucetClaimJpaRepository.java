package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.FaucetClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FaucetClaimJpaRepository extends JpaRepository<FaucetClaimEntity, String> {

    Optional<FaucetClaimEntity> findFirstByWalletAddressIgnoreCaseOrderByClaimedAtDesc(String walletAddress);

    List<FaucetClaimEntity> findAllByOrderByClaimedAtDesc();

    List<FaucetClaimEntity> findAllByWalletAddressIgnoreCaseOrderByClaimedAtDesc(String walletAddress);
}
