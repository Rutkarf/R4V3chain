package io.r4v3chain.backend.market.dto;

import java.util.List;

public record MarketCheckoutRequest(
        String walletAddress,
        List<MarketCartItemDto> items
) {}
