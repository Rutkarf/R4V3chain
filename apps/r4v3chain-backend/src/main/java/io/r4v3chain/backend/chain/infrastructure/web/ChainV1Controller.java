package io.r4v3chain.backend.chain.infrastructure.web;

import io.r4v3chain.backend.chain.ChainConfigService;
import io.r4v3chain.backend.chain.dto.ChainConfigResponse;
import io.r4v3chain.backend.config.ApiRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiRoutes.CHAIN_V1_PREFIX)
public class ChainV1Controller {

    private final ChainConfigService chainConfigService;

    public ChainV1Controller(ChainConfigService chainConfigService) {
        this.chainConfigService = chainConfigService;
    }

    @GetMapping("/config")
    public ChainConfigResponse config() {
        return chainConfigService.getConfig();
    }
}
