package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.FaqQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqQuestionJpaRepository extends JpaRepository<FaqQuestionEntity, String> {
}
