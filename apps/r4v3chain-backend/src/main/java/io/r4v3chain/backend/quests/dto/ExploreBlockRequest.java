package io.r4v3chain.backend.quests.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ExploreBlockRequest(
        @NotNull @Min(0) Integer blockIndex
) {
}
