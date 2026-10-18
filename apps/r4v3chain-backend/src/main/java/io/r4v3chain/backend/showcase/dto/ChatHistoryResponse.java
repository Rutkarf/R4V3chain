package io.r4v3chain.backend.showcase.dto;

import java.util.List;

public record ChatHistoryResponse(
        String roomId,
        List<ChatMessageResponse> messages
) {}
