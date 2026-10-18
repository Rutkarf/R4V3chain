package io.r4v3chain.backend.faucet.application;

import io.r4v3chain.backend.faucet.dto.FaucetClaimRequest;
import io.r4v3chain.backend.faucet.dto.FaucetClaimResponse;
import io.r4v3chain.backend.faucet.dto.FaucetConfigResponse;
import io.r4v3chain.backend.faucet.dto.FaucetStateResponse;
import io.r4v3chain.backend.faucet.model.FaucetClaim;

import java.util.List;

public interface FaucetService {

    FaucetStateResponse getState(String walletAddress);

    FaucetClaimResponse claim(FaucetClaimRequest request, String authorizationHeader);

    FaucetConfigResponse getConfig();

    List<FaucetClaim> getClaimsForWallet(String walletAddress);

    List<FaucetClaim> getClaimsForWallet(String walletAddress, int offset, int limit);
}