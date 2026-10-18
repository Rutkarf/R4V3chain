package io.r4v3chain.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record EmailResendRequest(@NotBlank String verificationId) {
}
