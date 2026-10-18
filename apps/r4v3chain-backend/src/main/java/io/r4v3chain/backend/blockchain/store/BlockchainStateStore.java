package io.r4v3chain.backend.blockchain.store;

import io.r4v3chain.backend.blockchain.BlockchainSnapshot;
import io.r4v3chain.backend.blockchain.model.Block;
import io.r4v3chain.backend.blockchain.model.PendingTransaction;

import java.util.List;

public interface BlockchainStateStore {

    BlockchainSnapshot load();

    void saveBlocks(List<Block> blocks);

    void savePendingPool(List<PendingTransaction> pendingPool);

    void saveAll(List<Block> blocks, List<PendingTransaction> pendingPool);
}
