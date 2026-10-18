package io.r4v3chain.backend.quests.dto;

import java.math.BigDecimal;

public record QuestWeeklyCatalogItem(
        BigDecimal rewardMts,
        int xpBoostPercent
) {
}
