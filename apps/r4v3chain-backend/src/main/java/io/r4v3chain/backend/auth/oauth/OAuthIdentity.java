package io.r4v3chain.backend.auth.oauth;

public record OAuthIdentity(
        String id,
        String userId,
        OAuthProvider provider,
        String providerSubject,
        long createdAt
) {
}
