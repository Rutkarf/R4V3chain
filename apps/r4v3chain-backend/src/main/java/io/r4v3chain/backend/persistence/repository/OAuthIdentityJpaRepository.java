package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.OAuthIdentityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OAuthIdentityJpaRepository extends JpaRepository<OAuthIdentityEntity, UUID> {

    Optional<OAuthIdentityEntity> findByProviderAndProviderSubject(String provider, String providerSubject);
}
