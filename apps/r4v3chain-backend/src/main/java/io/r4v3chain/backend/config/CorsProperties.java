package io.r4v3chain.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import io.r4v3chain.backend.shared.config.CorsConfig;

@ConfigurationProperties(prefix = "r4v3chain.cors")
public class CorsProperties {

    private List<String> allowedOriginPatterns = new ArrayList<>(CorsConfig.DEFAULT_ALLOWED_ORIGIN_PATTERNS);

    public List<String> getAllowedOriginPatterns() {
        return allowedOriginPatterns;
    }

    public void setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
        this.allowedOriginPatterns = allowedOriginPatterns == null
                ? new ArrayList<>(CorsConfig.DEFAULT_ALLOWED_ORIGIN_PATTERNS)
                : new ArrayList<>(allowedOriginPatterns);
    }
}
