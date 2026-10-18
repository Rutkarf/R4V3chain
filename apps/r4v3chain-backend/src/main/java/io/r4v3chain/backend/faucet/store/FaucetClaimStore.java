package io.r4v3chain.backend.faucet.store;

import io.r4v3chain.backend.faucet.model.FaucetClaim;

import java.util.List;
import java.util.Optional;

public interface FaucetClaimStore {

    FaucetClaim save(FaucetClaim claim);

    Optional<FaucetClaim> findLastClaimByWallet(String walletAddress);

    List<FaucetClaim> findAllOrderByClaimedAtDesc();

    List<FaucetClaim> findAllByWalletOrderByClaimedAtDesc(String walletAddress);
}
