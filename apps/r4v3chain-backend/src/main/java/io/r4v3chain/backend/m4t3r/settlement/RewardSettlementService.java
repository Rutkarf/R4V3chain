package io.r4v3chain.backend.m4t3r.settlement;

import io.r4v3chain.backend.m4t3r.model.M4t3rReward;

public interface RewardSettlementService {

    SettlementResult settle(M4t3rReward reward);
}
