package io.r4v3chain.backend.ops.infrastructure.web;

import io.r4v3chain.backend.ops.dto.HealthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Value("${r4v3chain.persistence.mode:memory}")
    private String persistenceMode;

    @GetMapping("/api/health")
    public HealthResponse getHealth() {
        return new HealthResponse(true, "r4v3chain-backend", persistenceMode);
    }
}
