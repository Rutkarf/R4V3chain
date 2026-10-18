package io.r4v3chain.backend.m4t3r.dto;

import java.util.List;

public record M4t3rRewardHistoryResponse(
        String walletAddress,
        int total,
        List<M4t3rRewardDto> rewards
) {
}
