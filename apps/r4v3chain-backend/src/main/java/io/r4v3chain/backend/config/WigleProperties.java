package io.r4v3chain.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "r4v3chain.wigle")
public record WigleProperties(
        boolean mockEnabled,
        String apiName,
        String apiToken,
        long cacheTtlMs,
        double coordinatePrecisionDegrees
) {
    public WigleProperties {
        if (cacheTtlMs <= 0) {
            cacheTtlMs = 300_000L;
        }
        if (coordinatePrecisionDegrees <= 0) {
            coordinatePrecisionDegrees = 0.0004;
        }
    }
}
