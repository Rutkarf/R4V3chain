package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, String> {

    List<ChatMessageEntity> findAllByOrderBySentAtAsc();
}
