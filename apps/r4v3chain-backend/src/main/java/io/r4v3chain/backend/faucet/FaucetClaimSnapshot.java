package io.r4v3chain.backend.faucet;

import io.r4v3chain.backend.faucet.model.FaucetClaim;

import java.util.ArrayList;
import java.util.List;

public class FaucetClaimSnapshot {

    private List<FaucetClaim> claims = new ArrayList<>();

    public List<FaucetClaim> getClaims() {
        return claims;
    }

    public void setClaims(List<FaucetClaim> claims) {
        this.claims = claims != null ? claims : new ArrayList<>();
    }
}
