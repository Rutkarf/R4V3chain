package io.r4v3chain.backend.quests.dto;

public record QuestTaskResponse(
        int progress,
        boolean claimed
) {
}
