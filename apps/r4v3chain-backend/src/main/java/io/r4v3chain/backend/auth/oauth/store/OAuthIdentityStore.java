package io.r4v3chain.backend.auth.oauth.store;

import io.r4v3chain.backend.auth.oauth.OAuthIdentity;
import io.r4v3chain.backend.auth.oauth.OAuthProvider;

import java.util.Optional;

public interface OAuthIdentityStore {

    Optional<OAuthIdentity> findByProviderSubject(OAuthProvider provider, String providerSubject);

    OAuthIdentity create(OAuthIdentity identity);
}
