package io.r4v3chain.backend.showcase.dto;

import java.util.List;

public record NewsFeedResponse(
        String headline,
        String lastTransaction,
        String featuredId,
        List<NewsItemResponse> items,
        List<String> categories,
        String liveActivity,
        String lastRefreshedAt,
        int totalCount,
        boolean hasMore
) {}
