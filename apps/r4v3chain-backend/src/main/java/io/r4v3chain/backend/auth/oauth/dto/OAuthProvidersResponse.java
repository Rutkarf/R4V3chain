package io.r4v3chain.backend.auth.oauth.dto;

import java.util.List;

public record OAuthProvidersResponse(List<OAuthProviderInfo> providers) {
}
