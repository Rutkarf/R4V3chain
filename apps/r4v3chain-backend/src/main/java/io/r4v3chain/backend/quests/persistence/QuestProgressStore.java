package io.r4v3chain.backend.quests.persistence;

import io.r4v3chain.backend.quests.model.QuestProgressState;

import java.util.Optional;

public interface QuestProgressStore {

    Optional<QuestProgressState> findByUserId(String userId);

    QuestProgressState save(String userId, QuestProgressState state);
}
