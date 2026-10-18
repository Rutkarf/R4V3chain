package io.r4v3chain.backend.chain.dto;

public record ChainConfigResponse(
        long chainId,
        String networkName,
        String nativeToken,
        String addressSchemeDefault,
        String signingPayloadVersion,
        boolean evmCompatible
) {
}
