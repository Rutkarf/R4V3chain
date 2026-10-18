package io.r4v3chain.backend.quests.dto;

import java.math.BigDecimal;

public record QuestMissionCatalogItem(
        String id,
        String title,
        String description,
        BigDecimal rewardMts,
        int rewardXp,
        int progressTarget
) {
}
