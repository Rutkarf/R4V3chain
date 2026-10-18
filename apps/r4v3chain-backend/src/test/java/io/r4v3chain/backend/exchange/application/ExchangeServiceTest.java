package io.r4v3chain.backend.exchange.application;

import io.r4v3chain.backend.auth.application.AuthService;
import io.r4v3chain.backend.auth.model.UserAccount;
import io.r4v3chain.backend.exchange.dto.ExchangeSwapRequest;
import io.r4v3chain.backend.exchange.dto.ExchangeSwapResponse;
import io.r4v3chain.backend.exchange.JsonExchangeLedgerStore;
import io.r4v3chain.backend.exchange.store.ExchangeLedgerStore;
import io.r4v3chain.backend.ops.ApplicationMetricsCollector;
import io.r4v3chain.backend.showcase.application.LaunchLabService;
import io.r4v3chain.backend.showcase.application.NewsService;
import io.r4v3chain.backend.blockchain.application.BlockchainService;
import io.r4v3chain.backend.blockchain.application.PendingTransactionService;
import io.r4v3chain.backend.exchange.application.CryptoRatesProxyService;
import io.r4v3chain.backend.exchange.application.ExchangeService;
import io.r4v3chain.backend.wallet.JsonWalletBalanceStore;
import io.r4v3chain.backend.wallet.dto.WalletPortfolioResponse;
import io.r4v3chain.backend.wallet.store.WalletBalanceStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExchangeServiceTest {

    @TempDir
    Path tempDir;

    private ExchangeLedgerStore ledgerStore;
    private WalletBalanceStore walletBalanceStore;
    private BlockchainService blockchainService;
    private CryptoRatesProxyService cryptoRates;
    private PendingTransactionService pendingTransactionService;
    private NewsService newsService;
    private LaunchLabService launchLabService;
    private AuthService authService;
    private ExchangeService exchangeService;

    @BeforeEach
    void setUp() {
        Path ledgerPath = tempDir.resolve("exchange-ledger.json");
        Path balancesPath = tempDir.resolve("wallet-balances.json");
        var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        ledgerStore = new JsonExchangeLedgerStore(objectMapper, ledgerPath.toString());
        walletBalanceStore = new JsonWalletBalanceStore(objectMapper, balancesPath.toString());

        blockchainService = mock(BlockchainService.class);
        cryptoRates = mock(CryptoRatesProxyService.class);
        pendingTransactionService = mock(PendingTransactionService.class);
        newsService = mock(NewsService.class);
        launchLabService = mock(LaunchLabService.class);
        authService = mock(AuthService.class);

        when(blockchainService.getBalance(anyString())).thenReturn(BigDecimal.ZERO);
        when(cryptoRates.getEurUnitPrice("R4V3")).thenReturn(BigDecimal.ONE);
        when(launchLabService.listSymbols()).thenReturn(java.util.List.of("R4V3", "LABZ"));
        when(launchLabService.isLaunchToken("LABZ")).thenReturn(true);
        when(authService.requireAuthenticatedAccount(anyString())).thenReturn(
                new UserAccount("user-1", "alice", "alice@r4v3chain.dev", "hash", "salt", 0L)
        );
        doNothing().when(authService).ensureWalletOwnership(any(UserAccount.class), anyString());

        exchangeService = new ExchangeService(
                blockchainService,
                cryptoRates,
                ledgerStore,
                walletBalanceStore,
                pendingTransactionService,
                newsService,
                launchLabService,
                authService,
                new ApplicationMetricsCollector()
        );
    }

  @Test
  void seedWelcomeCredits_isDisabled() {
    String wallet = "abc123wallet";

    assertThat(exchangeService.seedWelcomeCredits(wallet)).isFalse();
    assertThat(exchangeService.getPanel(wallet, "R4V3", "LABZ").fromBalance())
                .isEqualByComparingTo("0");
        assertThat(exchangeService.getPanel(wallet, "LABZ", "R4V3").fromBalance())
                .isEqualByComparingTo("0");
    }

    @Test
    void swap_updatesPersistedBalances() {
        String wallet = "abc123wallet";
        when(launchLabService.listSymbols()).thenReturn(java.util.List.of("R4V3", "LABZ"));
        when(launchLabService.isLaunchToken("LABZ")).thenReturn(true);
        when(blockchainService.getBalance(wallet)).thenReturn(new BigDecimal("100"));
        when(cryptoRates.getEurUnitPrice("R4V3")).thenReturn(BigDecimal.ONE);

        ExchangeSwapResponse response = exchangeService.swap(
                new ExchangeSwapRequest("R4V3", "LABZ", new BigDecimal("10"), wallet),
                "Bearer test-token"
        );

        assertThat(response.amountOut()).isGreaterThan(BigDecimal.ZERO);
        assertThat(exchangeService.getPanel(wallet, "R4V3", "LABZ").fromBalance())
                .isEqualByComparingTo("90");
        assertThat(exchangeService.getPanel(wallet, "R4V3", "LABZ").toBalance())
                .isGreaterThan(BigDecimal.ZERO);

        WalletPortfolioResponse portfolio = exchangeService.getPortfolio(wallet);
        assertThat(portfolio.holdings()).isNotEmpty();
        assertThat(portfolio.holdings().get(0).token()).isEqualTo("R4V3");
        assertThat(new BigDecimal(portfolio.holdings().get(0).balance())).isEqualByComparingTo("90");
        assertThat(portfolio.otherTokenCount()).isGreaterThan(0);
        assertThat(walletBalanceStore.find(wallet, "R4V3")).isPresent();
        assertThat(walletBalanceStore.find(wallet, "LABZ")).isPresent();

        verify(newsService).publishSwapEvent(anyString(), any(ExchangeSwapResponse.class));
    }

    @Test
    void swap_rejectsNonLaunchpadTokens() {
        assertThat(org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> exchangeService.swap(
                        new ExchangeSwapRequest("BTC", "R4V3", new BigDecimal("0.001"), "abc123wallet"),
                        "Bearer test-token"
                )
        ).getMessage()).contains("Unsupported token");
    }
}
