package io.r4v3chain.backend.explorer.application;

import io.r4v3chain.backend.explorer.dto.ExplorerSearchResponse;
import io.r4v3chain.backend.blockchain.application.BlockchainService;
import io.r4v3chain.backend.showcase.application.MarketChartService;
import io.r4v3chain.backend.blockchain.application.BlockchainValidationService;
import io.r4v3chain.backend.blockchain.application.TransactionPoolService;
import io.r4v3chain.backend.support.BlockchainTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ExplorerSearchServiceTest {

    @Mock
    private BlockchainValidationService validationService;

    @Mock
    private MarketChartService marketChartService;

    private ExplorerSearchService explorerSearchService;

    @BeforeEach
    void setUp() {
        var store = BlockchainTestSupport.inMemoryStore();
        TransactionPoolService transactionPoolService = BlockchainTestSupport.newTransactionPool(store);
        BlockchainService blockchainService = BlockchainTestSupport.newBlockchainService(
                store,
                validationService,
                marketChartService,
                transactionPoolService
        );
        explorerSearchService = new ExplorerSearchService(blockchainService);
    }

    @Test
    void searchReturnsGenesisBlockByIndex() {
        ExplorerSearchResponse response = explorerSearchService.search("0");

        assertThat(response.results())
                .isNotEmpty()
                .anyMatch(result -> "BLOCK".equals(result.kind()) && Integer.valueOf(0).equals(result.blockIndex()));
    }

    @Test
    void searchReturnsEmptyForBlankQuery() {
        ExplorerSearchResponse response = explorerSearchService.search("   ");

        assertThat(response.results()).isEmpty();
    }
}
