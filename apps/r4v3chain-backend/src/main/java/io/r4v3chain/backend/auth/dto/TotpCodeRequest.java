package io.r4v3chain.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TotpCodeRequest(@NotBlank @Pattern(regexp = "\\d{6}") String code) {
}
