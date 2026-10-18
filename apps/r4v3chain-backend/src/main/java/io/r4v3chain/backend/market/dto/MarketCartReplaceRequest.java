package io.r4v3chain.backend.market.dto;

import java.util.List;

public record MarketCartReplaceRequest(List<MarketCartItemDto> items) {}
