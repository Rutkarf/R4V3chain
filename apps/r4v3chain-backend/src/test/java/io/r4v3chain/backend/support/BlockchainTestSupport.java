package io.r4v3chain.backend.support;

import io.r4v3chain.backend.blockchain.InMemoryBlockchainStateStore;
import io.r4v3chain.backend.blockchain.store.BlockchainStateStore;
import io.r4v3chain.backend.ops.ApplicationMetricsCollector;
import io.r4v3chain.backend.blockchain.application.BlockchainService;
import io.r4v3chain.backend.blockchain.application.BlockchainValidationService;
import io.r4v3chain.backend.blockchain.application.TransactionPoolService;
import io.r4v3chain.backend.showcase.application.MarketChartService;

public final class BlockchainTestSupport {

    private BlockchainTestSupport() {
    }

    public static InMemoryBlockchainStateStore inMemoryStore() {
        return new InMemoryBlockchainStateStore();
    }

    public static TransactionPoolService newTransactionPool(BlockchainStateStore store) {
        TransactionPoolService service = new TransactionPoolService(store);
        service.loadFromStore();
        return service;
    }

    public static BlockchainService newBlockchainService(
            BlockchainStateStore store,
            BlockchainValidationService validationService,
            MarketChartService marketChartService,
            TransactionPoolService transactionPoolService
    ) {
        BlockchainService service = new BlockchainService(
                store,
                validationService,
                marketChartService,
                transactionPoolService,
                new ApplicationMetricsCollector()
        );
        service.loadFromStore();
        return service;
    }
}
