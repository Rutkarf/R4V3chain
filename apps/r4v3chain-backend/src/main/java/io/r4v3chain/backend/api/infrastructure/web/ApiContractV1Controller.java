package io.r4v3chain.backend.api.infrastructure.web;

import io.r4v3chain.backend.api.ApiContractCatalog;
import io.r4v3chain.backend.api.ApiContractV1Response;
import io.r4v3chain.backend.config.ApiRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiRoutes.API_V1_PREFIX)
public class ApiContractV1Controller {

    @GetMapping("/contract")
    public ApiContractV1Response contract() {
        return ApiContractCatalog.build();
    }
}
