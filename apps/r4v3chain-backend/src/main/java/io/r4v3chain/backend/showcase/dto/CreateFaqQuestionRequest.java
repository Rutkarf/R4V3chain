package io.r4v3chain.backend.showcase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateFaqQuestionRequest(
        @NotBlank @Size(min = 8, max = 200) String title,
        @NotBlank @Size(min = 12, max = 2000) String body
) {}
