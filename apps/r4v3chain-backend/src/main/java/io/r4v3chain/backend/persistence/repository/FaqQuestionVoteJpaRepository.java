package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.FaqQuestionVoteEntity;
import io.r4v3chain.backend.persistence.entity.FaqQuestionVoteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqQuestionVoteJpaRepository extends JpaRepository<FaqQuestionVoteEntity, FaqQuestionVoteId> {
}
