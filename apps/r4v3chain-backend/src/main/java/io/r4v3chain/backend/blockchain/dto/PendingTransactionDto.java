package io.r4v3chain.backend.blockchain.dto;

public record PendingTransactionDto(
        String id,
        String hash,
        String data,
        long createdAt
) {}