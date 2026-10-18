package io.r4v3chain.backend.auth.jwt;

public record JwtClaims(
        String subject,
        String role,
        long issuedAtEpochSeconds,
        long expiresAtEpochSeconds,
        String tokenId
) {
}
