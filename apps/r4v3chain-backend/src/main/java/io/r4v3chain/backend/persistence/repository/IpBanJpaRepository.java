package io.r4v3chain.backend.persistence.repository;

import io.r4v3chain.backend.persistence.entity.IpBanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpBanJpaRepository extends JpaRepository<IpBanEntity, String> {
    boolean existsByIpAddress(String ipAddress);
}
