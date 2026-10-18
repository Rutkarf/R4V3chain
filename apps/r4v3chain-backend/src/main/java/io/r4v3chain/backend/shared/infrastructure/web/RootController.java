package io.r4v3chain.backend.shared.infrastructure.web;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "ok", true,
                "service", "r4v3chain-backend",
                "health", "/api/health",
                "message", "R4V3chain backend API. Frontend Angular sur Cloudflare Pages (*.pages.dev)."
        );
    }
}
