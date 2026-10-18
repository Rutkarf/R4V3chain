package io.r4v3chain.backend.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r4v3chain.backend.auth.application.AccountFactorService;
import io.r4v3chain.backend.auth.application.AuthService;
import io.r4v3chain.backend.auth.application.AuthTokenResolver;
import io.r4v3chain.backend.auth.audit.AuthAuditService;
import io.r4v3chain.backend.auth.audit.InMemoryAuthAuditStore;
import io.r4v3chain.backend.auth.dto.EmailCodeRequest;
import io.r4v3chain.backend.auth.dto.LoginRequest;
import io.r4v3chain.backend.auth.dto.RegisterRequest;
import io.r4v3chain.backend.auth.dto.TotpCodeRequest;
import io.r4v3chain.backend.auth.dto.TotpVerifyRequest;
import io.r4v3chain.backend.auth.jwt.NativeJwtService;
import io.r4v3chain.backend.auth.mail.AuthMailer;
import io.r4v3chain.backend.auth.persistence.InMemoryRefreshTokenStore;
import io.r4v3chain.backend.auth.persistence.InMemorySessionStore;
import io.r4v3chain.backend.auth.persistence.JsonUserAccountStore;
import io.r4v3chain.backend.auth.security.TotpAuthenticator;
import io.r4v3chain.backend.config.AuthProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class AuthFactorFlowTest {

    @TempDir
    Path tempDir;

    private AuthService authService;
    private NativeJwtService jwtService;
    private final CapturingMailer mailer = new CapturingMailer();

    @BeforeEach
    void setUp() {
        JsonUserAccountStore userStore = new JsonUserAccountStore(
                new ObjectMapper(),
                tempDir.resolve("auth-users.json").toString()
        );
        userStore.loadFromDisk();
        AuthProperties authProperties = new AuthProperties();
        authProperties.setEmailVerificationRequired(true);
        jwtService = new NativeJwtService(authProperties);
        InMemoryRefreshTokenStore refreshTokenStore = new InMemoryRefreshTokenStore(authProperties);
        InMemorySessionStore sessionStore = new InMemorySessionStore(3600, authProperties);
        AuthTokenResolver authTokenResolver = new AuthTokenResolver(
                jwtService,
                refreshTokenStore,
                sessionStore,
                userStore,
                authProperties
        );
        AccountFactorService factors = new AccountFactorService(userStore, jwtService, authProperties, mailer);
        authService = new AuthService(
                userStore,
                refreshTokenStore,
                authTokenResolver,
                jwtService,
                authProperties,
                new AuthAuditService(new InMemoryAuthAuditStore()),
                null,
                null,
                factors
        );
    }

    @Test
    void registerSendsEmailCodeThenOpensSession() {
        var pending = authService.register(
                new RegisterRequest("ada", "ada@r4v3chain.review", "password123"),
                "127.0.0.1"
        );

        assertThat(pending.status()).isEqualTo("EMAIL_VERIFICATION");
        assertThat(pending.token()).isNull();
        assertThat(mailer.lastCode).hasSize(6);
        assertThat(mailer.lastTo).isEqualTo("ada@r4v3chain.review");

        var session = authService.confirmEmail(
                new EmailCodeRequest(pending.verificationId(), mailer.lastCode),
                "127.0.0.1"
        );
        assertThat(session.status()).isEqualTo("AUTHENTICATED");
        assertThat(session.token()).isNotBlank();
    }

    @Test
    void totpChallengeIsNotAnAccessToken() {
        var pending = authService.register(
                new RegisterRequest("ada", "ada@r4v3chain.review", "password123"),
                "127.0.0.1"
        );
        authService.confirmEmail(new EmailCodeRequest(pending.verificationId(), mailer.lastCode), "127.0.0.1");

        var setup = authService.beginTotpSetup(authService.login(
                new LoginRequest("ada", "password123"),
                "127.0.0.1"
        ).token());
        authService.enableTotp(
                authService.login(new LoginRequest("ada", "password123"), "127.0.0.1").token(),
                new TotpCodeRequest(TotpAuthenticator.currentCode(setup.secret()))
        );

        var challenge = authService.login(new LoginRequest("ada", "password123"), "127.0.0.1");
        assertThat(challenge.status()).isEqualTo("TWO_FACTOR");
        assertThat(challenge.token()).isNull();
        assertThat(jwtService.parseAndValidate(challenge.challengeToken())).isEmpty();

        var session = authService.confirmTotp(
                new TotpVerifyRequest(challenge.challengeToken(), TotpAuthenticator.currentCode(setup.secret())),
                "127.0.0.1"
        );
        assertThat(session.token()).isNotBlank();
        assertThat(session.user().totpEnabled()).isTrue();
    }

    private static final class CapturingMailer implements AuthMailer {
        private String lastCode;
        private String lastTo;

        @Override
        public boolean isConfigured() {
            return true;
        }

        @Override
        public void sendVerificationCode(String to, String username, String code) {
            this.lastTo = to;
            this.lastCode = code;
        }
    }
}
