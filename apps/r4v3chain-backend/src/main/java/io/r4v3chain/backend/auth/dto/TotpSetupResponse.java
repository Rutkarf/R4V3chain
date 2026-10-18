package io.r4v3chain.backend.auth.dto;

public record TotpSetupResponse(String secret, String otpauthUrl) {
}
