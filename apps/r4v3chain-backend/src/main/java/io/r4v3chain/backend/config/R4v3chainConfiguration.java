package io.r4v3chain.backend.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        CorsProperties.class,
        RateLimitProperties.class,
        SecurityProperties.class,
        DataImportProperties.class,
        OpsProperties.class,
        ProductProperties.class,
        AuthProperties.class,
        MailProperties.class,
        ChainProperties.class,
        OAuthProperties.class,
        WigleProperties.class,
        io.r4v3chain.backend.admin.config.AdminSeedProperties.class
})
public class R4v3chainConfiguration {
}
