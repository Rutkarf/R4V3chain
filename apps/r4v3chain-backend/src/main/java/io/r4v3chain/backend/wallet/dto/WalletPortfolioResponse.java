package io.r4v3chain.backend.wallet.dto;

import java.util.List;

public record WalletPortfolioResponse(
        String walletAddress,
        String nativeToken,
        List<WalletHoldingDto> holdings,
        int otherTokenCount,
        long syncedAtEpochMs
) {
}
