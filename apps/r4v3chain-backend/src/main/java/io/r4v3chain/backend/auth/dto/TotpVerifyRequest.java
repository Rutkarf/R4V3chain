package io.r4v3chain.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TotpVerifyRequest(
        @NotBlank String challengeToken,
        @NotBlank @Pattern(regexp = "\\d{6}") String code
) {
}
