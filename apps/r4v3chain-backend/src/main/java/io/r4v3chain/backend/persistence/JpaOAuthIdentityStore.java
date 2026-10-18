package io.r4v3chain.backend.persistence;

import io.r4v3chain.backend.auth.oauth.OAuthIdentity;
import io.r4v3chain.backend.auth.oauth.OAuthProvider;
import io.r4v3chain.backend.auth.oauth.store.OAuthIdentityStore;
import io.r4v3chain.backend.persistence.entity.OAuthIdentityEntity;
import io.r4v3chain.backend.persistence.repository.OAuthIdentityJpaRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@ConditionalOnProperty(name = "r4v3chain.persistence.mode", havingValue = "postgres")
public class JpaOAuthIdentityStore implements OAuthIdentityStore {

    private final OAuthIdentityJpaRepository repository;

    public JpaOAuthIdentityStore(OAuthIdentityJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OAuthIdentity> findByProviderSubject(OAuthProvider provider, String providerSubject) {
        return repository
                .findByProviderAndProviderSubject(provider.id(), providerSubject)
                .map(JpaOAuthIdentityStore::toIdentity);
    }

    @Override
    @Transactional
    public OAuthIdentity create(OAuthIdentity identity) {
        OAuthIdentityEntity entity = new OAuthIdentityEntity();
        entity.setId(UUID.fromString(identity.id()));
        entity.setUserId(UUID.fromString(identity.userId()));
        entity.setProvider(identity.provider().id());
        entity.setProviderSubject(identity.providerSubject());
        entity.setCreatedAt(Instant.ofEpochMilli(identity.createdAt()));
        return toIdentity(repository.save(entity));
    }

    private static OAuthIdentity toIdentity(OAuthIdentityEntity entity) {
        return new OAuthIdentity(
                entity.getId().toString(),
                entity.getUserId().toString(),
                OAuthProvider.fromId(entity.getProvider()),
                entity.getProviderSubject(),
                entity.getCreatedAt().toEpochMilli()
        );
    }
}
