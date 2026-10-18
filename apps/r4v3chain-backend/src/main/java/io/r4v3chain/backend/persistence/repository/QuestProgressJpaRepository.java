package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.QuestProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestProgressJpaRepository extends JpaRepository<QuestProgressEntity, UUID> {
}
