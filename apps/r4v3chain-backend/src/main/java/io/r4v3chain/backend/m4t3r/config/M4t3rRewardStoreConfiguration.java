package io.r4v3chain.backend.m4t3r.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r4v3chain.backend.m4t3r.JsonM4t3rRewardStore;
import io.r4v3chain.backend.m4t3r.store.M4t3rRewardStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Store M4T3R JSON, mode memory. En postgres, {@code JpaM4t3rRewardStore} prend le relais.
 */
@Configuration
@ConditionalOnProperty(name = "r4v3chain.persistence.mode", havingValue = "memory", matchIfMissing = true)
public class M4t3rRewardStoreConfiguration {

    @Bean
    @ConditionalOnMissingBean(M4t3rRewardStore.class)
    JsonM4t3rRewardStore m4t3rRewardStore(ObjectMapper objectMapper, M4t3rRewardConfig config) {
        return new JsonM4t3rRewardStore(objectMapper, config);
    }
}
