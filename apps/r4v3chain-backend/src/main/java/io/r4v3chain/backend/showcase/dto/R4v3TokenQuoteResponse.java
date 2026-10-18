package io.r4v3chain.backend.showcase.dto;

public record R4v3TokenQuoteResponse(
        String symbol,
        String priceVsR4v3,
        String change,
        boolean positive
) {}
