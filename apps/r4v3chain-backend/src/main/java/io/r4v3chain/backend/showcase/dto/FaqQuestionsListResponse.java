package io.r4v3chain.backend.showcase.dto;

import java.util.List;

public record FaqQuestionsListResponse(
        List<FaqQuestionResponse> questions,
        int totalCount
) {}
