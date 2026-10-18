package io.r4v3chain.backend.auth.oauth.dto;

import jakarta.validation.constraints.NotBlank;

public record OAuthExchangeRequest(@NotBlank String code) {
}
