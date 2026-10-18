package io.r4v3chain.backend.showcase.dto;

import io.r4v3chain.backend.exchange.dto.CryptoRatePanelResponse;

import java.util.List;

public record R4v3ShowcaseResponse(
        CryptoRatePanelResponse panel,
        NewsFeedResponse news,
        List<R4v3TokenQuoteResponse> launchTokens,
        R4v3SwapStatsResponse swapStats,
        long ratesLatencyMs,
        String lastRefreshedAt
) {}
