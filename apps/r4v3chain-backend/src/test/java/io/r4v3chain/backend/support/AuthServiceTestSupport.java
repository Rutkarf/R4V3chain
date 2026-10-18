package io.r4v3chain.backend.support;

import io.r4v3chain.backend.auth.application.AuthService;
import io.r4v3chain.backend.auth.application.AuthTokenResolver;
import io.r4v3chain.backend.auth.persistence.InMemoryRefreshTokenStore;
import io.r4v3chain.backend.auth.persistence.InMemorySessionStore;
import io.r4v3chain.backend.auth.persistence.JsonUserAccountStore;
import io.r4v3chain.backend.auth.audit.AuthAuditService;
import io.r4v3chain.backend.auth.audit.InMemoryAuthAuditStore;
import io.r4v3chain.backend.auth.jwt.NativeJwtService;
import io.r4v3chain.backend.auth.store.UserAccountStore;
import io.r4v3chain.backend.config.AuthProperties;
import io.r4v3chain.backend.quests.application.QuestService;

public final class AuthServiceTestSupport {

    public static final String LOCAL_IP = "127.0.0.1";

    private AuthServiceTestSupport() {
    }

    public static AuthService createAuthService(UserAccountStore userStore) {
        return createAuthService(userStore, null);
    }

    public static AuthService createAuthService(UserAccountStore userStore, QuestService questService) {
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

        if (questService == null) {
            return new AuthService(
                    userStore,
                    refreshTokenStore,
                    authTokenResolver,
                    nativeJwtService,
                    authProperties,
                    authAuditService
            );
        }

        return new AuthService(
                userStore,
                refreshTokenStore,
                authTokenResolver,
                nativeJwtService,
                authProperties,
                authAuditService,
                questService,
                null
        );
    }

    public static AuthService createJsonAuthService(JsonUserAccountStore userStore) {
        return createAuthService(userStore);
    }

    public static AuthService createJsonAuthService(JsonUserAccountStore userStore, QuestService questService) {
        return createAuthService(userStore, questService);
    }
}
