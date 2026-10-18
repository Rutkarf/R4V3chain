package io.r4v3chain.backend.quests.dto;

import java.util.List;
import java.util.Set;

public record QuestCatalogResponse(
        List<QuestDailyTaskCatalogItem> dailyTasks,
        QuestMissionCatalogItem mission,
        QuestWeeklyCatalogItem weekly,
        Set<String> serverHookedTaskIds
) {
}
