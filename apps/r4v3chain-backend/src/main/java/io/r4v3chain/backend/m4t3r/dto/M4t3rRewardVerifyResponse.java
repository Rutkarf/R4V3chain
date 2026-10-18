package io.r4v3chain.backend.m4t3r.dto;

public record M4t3rRewardVerifyResponse(
        String rewardId,
        String proofHash,
        String serverSignature,
        boolean valid,
        String algorithm,
        String keyId
) {
}
