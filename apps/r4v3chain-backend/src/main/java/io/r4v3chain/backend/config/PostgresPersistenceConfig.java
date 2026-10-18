package io.r4v3chain.backend.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConditionalOnProperty(name = "r4v3chain.persistence.mode", havingValue = "postgres")
@EnableJpaRepositories(basePackages = "io.r4v3chain.backend.persistence.repository")
@EntityScan(basePackages = "io.r4v3chain.backend.persistence.entity")
@EnableTransactionManagement
public class PostgresPersistenceConfig {
}
