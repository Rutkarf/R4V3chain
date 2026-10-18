package io.r4v3chain.backend.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r4v3chain.backend.auth.application.AuthException;
import io.r4v3chain.backend.auth.application.AuthService;
import io.r4v3chain.backend.auth.application.AuthTokenResolver;
import io.r4v3chain.backend.auth.model.UserAccount;
import io.r4v3chain.backend.auth.persistence.InMemoryRefreshTokenStore;
import io.r4v3chain.backend.auth.persistence.InMemorySessionStore;
import io.r4v3chain.backend.auth.persistence.JsonUserAccountStore;
import io.r4v3chain.backend.auth.audit.AuthAuditService;
import io.r4v3chain.backend.auth.audit.InMemoryAuthAuditStore;
import io.r4v3chain.backend.auth.dto.LinkWalletRequest;
import io.r4v3chain.backend.auth.dto.LoginRequest;
import io.r4v3chain.backend.auth.dto.RefreshRequest;
import io.r4v3chain.backend.auth.dto.RegisterRequest;
import io.r4v3chain.backend.auth.jwt.NativeJwtService;
import io.r4v3chain.backend.config.AuthProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.security.KeyPair;

import io.r4v3chain.backend.shared.utils.CryptoUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest {

    @TempDir
    Path tempDir;

    private AuthService authService;
    private JsonUserAccountStore userStore;

    @BeforeEach
    void setUp() {
        userStore = new JsonUserAccountStore(
                new ObjectMapper(),
                tempDir.resolve("auth-users.json").toString()
        );
        userStore.loadFromDisk();

        AuthProperties authProperties = new AuthProperties();
        NativeJwtService nativeJwtService = new NativeJwtService(authProperties);
        InMemoryRefreshTokenStore refreshTokenStore = new InMemoryRefreshTokenStore(authProperties);
        InMemorySessionStore sessionStore = new InMemorySessionStore(3600, authProperties);
        AuthTokenResolver authTokenResolver = new AuthTokenResolver(
                nativeJwtService,
                refreshTokenStore,
                sessionStore,
                userStore,
                authProperties
        );
        AuthAuditService authAuditService = new AuthAuditService(new InMemoryAuthAuditStore());

        authService = new AuthService(
                userStore,
                refreshTokenStore,
                authTokenResolver,
                nativeJwtService,
                authProperties,
                authAuditService
        );
    }

    @Test
    void registerLoginAndMe() {
        authService.register(new RegisterRequest("rutkarf", "rutkarf@r4v3chain.dev", "password123"), "127.0.0.1");

        var loginResponse = authService.login(new LoginRequest("rutkarf", "password123"), "127.0.0.1");
        assertThat(loginResponse.token()).isNotBlank();
        assertThat(loginResponse.accessToken()).isEqualTo(loginResponse.token());
        assertThat(loginResponse.refreshToken()).isNotBlank();
        assertThat(loginResponse.token().split("\\.")).hasSize(3);
        assertThat(loginResponse.user().username()).isEqualTo("rutkarf");
        assertThat(loginResponse.user().role()).isEqualTo("USER");

        var profile = authService.me("Bearer " + loginResponse.token());
        assertThat(profile.email()).isEqualTo("rutkarf@r4v3chain.dev");
        assertThat(profile.walletAddress()).isNull();
    }

    @Test
    void refreshRotatesTokens() {
        authService.register(new RegisterRequest("refreshuser", "refresh@r4v3chain.dev", "password123"), "127.0.0.1");
        var loginResponse = authService.login(new LoginRequest("refreshuser", "password123"), "127.0.0.1");

        var refreshed = authService.refresh(new RefreshRequest(loginResponse.refreshToken()), "127.0.0.1");
        assertThat(refreshed.accessToken()).isNotBlank();
        assertThat(refreshed.refreshToken()).isNotEqualTo(loginResponse.refreshToken());

        var profile = authService.me("Bearer " + refreshed.accessToken());
        assertThat(profile.username()).isEqualTo("refreshuser");
    }

    @Test
    void rejectsPasswordLoginForOAuthOnlyAccount() {
        UserAccount oauthAccount = new UserAccount(
                java.util.UUID.randomUUID().toString(),
                "oauth-user",
                "oauth@r4v3chain.dev",
                "$OAUTH$",
                "",
                System.currentTimeMillis()
        );
        userStore.create(oauthAccount);

        assertThatThrownBy(() ->
                authService.login(new LoginRequest("oauth@r4v3chain.dev", "password123"), "127.0.0.1")
        )
                .isInstanceOf(AuthException.class)
                .extracting("statusCode")
                .isEqualTo(401);
    }

    @Test
    void linksWalletToAccount() throws Exception {
        authService.register(new RegisterRequest("alice", "alice@r4v3chain.dev", "password123"), "127.0.0.1");
        var loginResponse = authService.login(new LoginRequest("alice", "password123"), "127.0.0.1");

        KeyPair keyPair = CryptoUtils.generateKeyPair();
        String publicKey = CryptoUtils.publicKeyToBase64(keyPair.getPublic());
        String walletAddress = CryptoUtils.addressFromPublicKey(keyPair.getPublic());

        var profile = authService.linkWallet(
                "Bearer " + loginResponse.token(),
                new LinkWalletRequest(walletAddress, publicKey)
        );

        assertThat(profile.walletAddress()).isEqualTo(walletAddress);
        assertThat(profile.walletPublicKey()).isEqualTo(publicKey);
    }

    @Test
    void rejectsShortPassword() {
        assertThatThrownBy(() ->
                authService.register(new RegisterRequest("alice", "alice@r4v3chain.dev", "abc"), "127.0.0.1")
        )
                .isInstanceOf(AuthException.class)
                .extracting("statusCode")
                .isEqualTo(400);
    }

    @Test
    void rejectsDuplicateUsername() {
        authService.register(new RegisterRequest("alice", "alice@r4v3chain.dev", "password123"), "127.0.0.1");

        assertThatThrownBy(() ->
                authService.register(new RegisterRequest("alice", "bob@r4v3chain.dev", "password123"), "127.0.0.1")
        )
                .isInstanceOf(AuthException.class)
                .extracting("statusCode")
                .isEqualTo(409);
    }

    @Test
    void rejectsInvalidCredentials() {
        authService.register(new RegisterRequest("alice", "alice@r4v3chain.dev", "password123"), "127.0.0.1");

        assertThatThrownBy(() ->
                authService.login(new LoginRequest("alice", "wrong-password"), "127.0.0.1")
        )
                .isInstanceOf(AuthException.class)
                .extracting("statusCode")
                .isEqualTo(401);
    }
}
