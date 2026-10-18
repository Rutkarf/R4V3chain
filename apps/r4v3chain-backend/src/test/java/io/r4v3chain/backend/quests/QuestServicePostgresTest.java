package io.r4v3chain.backend.quests;

import io.r4v3chain.backend.quests.application.QuestService;
import io.r4v3chain.backend.auth.dto.LinkWalletRequest;
import io.r4v3chain.backend.auth.dto.LoginRequest;
import io.r4v3chain.backend.auth.dto.RegisterRequest;
import io.r4v3chain.backend.shared.utils.CryptoUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.r4v3chain.backend.support.AuthServiceTestSupport;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest
@ActiveProfiles("postgres")
class QuestServicePostgresTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("r4v3chain")
            .withUsername("r4v3chain")
            .withPassword("r4v3chain");

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("r4v3chain.persistence.mode", () -> "postgres");
    }

    @Autowired
    private io.r4v3chain.backend.auth.application.AuthService authService;

    @Autowired
    private QuestService questService;

    @Test
    void persistsQuestProgressInPostgres() throws Exception {
        authService.register(
                new RegisterRequest("questpg", "questpg@r4v3chain.dev", "password123"),
                AuthServiceTestSupport.LOCAL_IP
        );
        var login = authService.login(new LoginRequest("questpg", "password123"), AuthServiceTestSupport.LOCAL_IP);
        String authHeader = "Bearer " + login.token();

        KeyPair keyPair = CryptoUtils.generateKeyPair();
        String walletAddress = CryptoUtils.addressFromPublicKey(keyPair.getPublic());
        authService.linkWallet(
                authHeader,
                new LinkWalletRequest(walletAddress, CryptoUtils.publicKeyToBase64(keyPair.getPublic()))
        );

        questService.recordProgressForUserId(login.user().id(), "faucet-claim", 1);

        var reloaded = questService.getState(authHeader);
        assertThat(reloaded.tasks().get("faucet-claim").progress()).isEqualTo(1);
        assertThat(reloaded.tasks().get("faucet-claim").claimed()).isFalse();
    }
}
