package io.r4v3chain.backend.wallet.dto;

public record WalletVerifyResponse(
        boolean valid,
        String address,
        String publicKey,
        String signingModel
) {
}
