package io.r4v3chain.backend.api;

public record ApiContractEndpoint(
        String method,
        String path,
        boolean authRequired,
        String description
) {
}
