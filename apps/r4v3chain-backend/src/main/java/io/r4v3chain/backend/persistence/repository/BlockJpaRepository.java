package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockJpaRepository extends JpaRepository<BlockEntity, Integer> {

    List<BlockEntity> findAllByOrderByBlockIndexAsc();
}
