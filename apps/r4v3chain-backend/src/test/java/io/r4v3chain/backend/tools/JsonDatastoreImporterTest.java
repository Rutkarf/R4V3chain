package io.r4v3chain.backend.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r4v3chain.backend.blockchain.BlockchainSnapshot;
import io.r4v3chain.backend.blockchain.store.BlockchainStateStore;
import io.r4v3chain.backend.config.DataImportProperties;
import io.r4v3chain.backend.exchange.store.ExchangeLedgerStore;
import io.r4v3chain.backend.faucet.store.FaucetClaimStore;
import io.r4v3chain.backend.blockchain.model.Block;
import io.r4v3chain.backend.persistence.repository.UserJpaRepository;
import io.r4v3chain.backend.quests.persistence.QuestProgressStore;
import io.r4v3chain.backend.showcase.chat.store.ChatMessageStore;
import io.r4v3chain.backend.showcase.launch.store.LaunchProjectStore;
import io.r4v3chain.backend.showcase.news.store.NewsItemStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JsonDatastoreImporterTest {

    @Mock
    private BlockchainStateStore blockchainStateStore;

    @Mock
    private UserJpaRepository userRepository;

    @Mock
    private QuestProgressStore questProgressStore;

    @Mock
    private FaucetClaimStore faucetClaimStore;

    @Mock
    private ExchangeLedgerStore exchangeLedgerStore;

    @Mock
    private LaunchProjectStore launchProjectStore;

    @Mock
    private ChatMessageStore chatMessageStore;

    @Mock
    private NewsItemStore newsItemStore;

    @TempDir
    Path tempDir;

    @Test
    void importAll_skipsMissingFiles() {
        DataImportProperties properties = missingOnlyProperties(tempDir);

        JsonDatastoreImporter importer = new JsonDatastoreImporter(
                new ObjectMapper(),
                properties,
                blockchainStateStore,
                userRepository,
                questProgressStore,
                faucetClaimStore,
                exchangeLedgerStore,
                launchProjectStore,
                chatMessageStore,
                newsItemStore
        );

        JsonDatastoreImportReport report = importer.importAll();

        assertThat(report.totalImported()).isZero();
        assertThat(report.getSkippedFiles()).hasSize(8);
    }

    private static DataImportProperties missingOnlyProperties(Path tempDir) {
        DataImportProperties properties = new DataImportProperties();
        properties.setBlockchainStatePath(tempDir.resolve("missing-blockchain.json").toString());
        properties.setAuthUsersPath(tempDir.resolve("missing-users.json").toString());
        properties.setQuestProgressPath(tempDir.resolve("missing-quests.json").toString());
        properties.setFaucetClaimsPath(tempDir.resolve("missing-faucet.json").toString());
        properties.setExchangeLedgerPath(tempDir.resolve("missing-exchange.json").toString());
        properties.setLaunchProjectsPath(tempDir.resolve("missing-launch.json").toString());
        properties.setChatMessagesPath(tempDir.resolve("missing-chat.json").toString());
        properties.setNewsItemsPath(tempDir.resolve("missing-news.json").toString());
        return properties;
    }

    @Test
    void importAll_importsBlockchainSnapshot() throws Exception {
        Path blockchainPath = tempDir.resolve("blockchain-state.json");
        Files.writeString(blockchainPath, """
                {
                  "blocks": [
                    {
                      "index": 0,
                      "timestamp": 1,
                      "hash": "genesis",
                      "previousHash": "0",
                      "nonce": 0,
                      "transactions": []
                    }
                  ],
                  "pendingPool": []
                }
                """);

        DataImportProperties properties = missingOnlyProperties(tempDir);
        properties.setBlockchainStatePath(blockchainPath.toString());

        JsonDatastoreImporter importer = new JsonDatastoreImporter(
                new ObjectMapper(),
                properties,
                blockchainStateStore,
                userRepository,
                questProgressStore,
                faucetClaimStore,
                exchangeLedgerStore,
                launchProjectStore,
                chatMessageStore,
                newsItemStore
        );

        JsonDatastoreImportReport report = importer.importAll();

        assertThat(report.getImportedCounts().get("blockchain")).isEqualTo(1);
        verify(blockchainStateStore).saveAll(anyList(), eq(List.of()));
    }
}
