package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.ChainAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChainAccountRepository extends JpaRepository<ChainAccountEntity, String> {
}
