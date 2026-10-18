package io.r4v3chain.backend.showcase.dto;

public record BannerResponse(
        String message1,
        String lastTransaction,
        String lastTransactionShort,
        int userCount
) {}