package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.PendingTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendingTransactionJpaRepository extends JpaRepository<PendingTransactionEntity, String> {

    List<PendingTransactionEntity> findAllByOrderByCreatedAtAsc();
}
