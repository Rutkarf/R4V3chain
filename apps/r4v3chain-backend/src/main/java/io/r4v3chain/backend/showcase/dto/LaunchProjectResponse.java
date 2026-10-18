package io.r4v3chain.backend.showcase.dto;

public record LaunchProjectResponse(
        String id,
        String name,
        String symbol,
        String status,
        String raised,
        String target,
        String logoUrl,
        String description,
        String chain,
        String whitepaperUrl,
        String website,
        String launchDate
) {}
