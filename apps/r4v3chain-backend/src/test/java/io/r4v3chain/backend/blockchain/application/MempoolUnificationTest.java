package io.r4v3chain.backend.blockchain.application;

import io.r4v3chain.backend.auth.model.UserAccount;
import io.r4v3chain.backend.auth.store.UserAccountStore;
import io.r4v3chain.backend.config.SecurityProperties;
import io.r4v3chain.backend.blockchain.dto.BlockValidationResult;
import io.r4v3chain.backend.blockchain.dto.PendingTransactionResponse;
import io.r4v3chain.backend.blockchain.model.Transaction;
import io.r4v3chain.backend.showcase.application.MarketChartService;
import io.r4v3chain.backend.support.BlockchainTestSupport;
import io.r4v3chain.backend.blockchain.application.BlockchainService;
import io.r4v3chain.backend.blockchain.application.BlockchainValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MempoolUnificationTest {

    @Mock
    private BlockchainValidationService validationService;

    @Mock
    private MarketChartService marketChartService;

    @Mock
    private UserAccountStore userAccountStore;

    private TransactionPoolService transactionPoolService;
    private BlockchainService blockchainService;
    private PendingTransactionServiceImpl pendingTransactionService;

    @BeforeEach
    void setUp() {
        var store = BlockchainTestSupport.inMemoryStore();
        transactionPoolService = BlockchainTestSupport.newTransactionPool(store);
        blockchainService = BlockchainTestSupport.newBlockchainService(
                store,
                validationService,
                marketChartService,
                transactionPoolService
        );
        pendingTransactionService = new PendingTransactionServiceImpl(
                transactionPoolService,
                blockchainService,
                new TransactionValidationService(new SecurityProperties()),
                userAccountStore
        );
    }

    private static io.r4v3chain.backend.blockchain.model.PendingTransaction signedPending(
            String id,
            UserAccount account
    ) {
        var pending = new io.r4v3chain.backend.blockchain.model.PendingTransaction();
        pending.setId(id);
        pending.setFromAddress(account.getWalletAddress());
        pending.setToAddress("bob");
        pending.setAmount(new BigDecimal("1.5"));
        pending.setData("payload");
        pending.setStatus("PENDING");
        pending.setCreatedAt(System.currentTimeMillis());
        pending.setSystemReward(false);
        pending.setSignature(PendingTransactionAttestation.sign(account, pending));
        pending.setHash("0123456789012345678901234567890123456789012345678901234567890");
        return pending;
    }

    private static UserAccount linkedAccount(String id, String wallet) {
        UserAccount account = new UserAccount();
        account.setId(id);
        account.setUsername("alice");
        account.setEmail("alice@r4v3chain.dev");
        account.setPasswordHash("hash");
        account.setPasswordSalt("");
        account.setCreatedAt(System.currentTimeMillis());
        account.setWalletAddress(wallet);
        account.setWalletPublicKey("public-key");
        return account;
    }

    @Test
    void blockchainAndPendingEndpointsReadSamePool() {
        UserAccount account = linkedAccount("user-1", "alice");
        var pending = signedPending("shared-tx", account);
        pending.setAmount(new BigDecimal("1.5"));

        transactionPoolService.add(pending);

        assertThat(blockchainService.getPendingTransactions())
                .extracting(Transaction::getId)
                .containsExactly("shared-tx");

        assertThat(pendingTransactionService.getPendingTransactions())
                .extracting(PendingTransactionResponse::getId)
                .containsExactly("shared-tx");
    }

    @Test
    void miningThroughBlockchainDrainsSharedPool() {
        when(validationService.validateBlockAgainstChain(any(), anyList()))
                .thenReturn(new BlockValidationResult(true, "ok"));

        UserAccount account = linkedAccount("user-2", "alice");
        var pending = signedPending("mine-me", account);
        pending.setAmount(new BigDecimal("1.0"));

        transactionPoolService.add(pending);

        blockchainService.minePendingTransactions("miner-1");

        assertThat(blockchainService.getPendingTransactions()).isEmpty();
        assertThat(pendingTransactionService.getPendingTransactions()).isEmpty();
    }
}
