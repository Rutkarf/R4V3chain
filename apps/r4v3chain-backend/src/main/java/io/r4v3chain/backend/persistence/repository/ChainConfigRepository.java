package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.ChainConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChainConfigRepository extends JpaRepository<ChainConfigEntity, String> {
}
