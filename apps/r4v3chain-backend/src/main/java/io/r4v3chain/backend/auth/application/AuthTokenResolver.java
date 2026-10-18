package io.r4v3chain.backend.auth.application;

import io.r4v3chain.backend.auth.model.UserAccount;
import io.r4v3chain.backend.auth.jwt.NativeJwtService;
import io.r4v3chain.backend.auth.store.RefreshTokenStore;
import io.r4v3chain.backend.auth.store.SessionStore;
import io.r4v3chain.backend.auth.store.UserAccountStore;
import io.r4v3chain.backend.config.AuthProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Résout un compte depuis un JWT. Le chemin session legacy n'est lu que si
 * {@code r4v3chain.auth.legacy-session-enabled} est vrai, et seulement pour un jeton
 * qui n'a pas exactement deux points. Ce drapeau est faux dans la configuration livrée :
 * le login n'écrit pas {@code auth_sessions}.
 */
@Service
public class AuthTokenResolver {

    private final NativeJwtService nativeJwtService;
    private final RefreshTokenStore refreshTokenStore;
    private final SessionStore sessionStore;
    private final UserAccountStore userAccountStore;
    private final AuthProperties authProperties;

    public AuthTokenResolver(
            NativeJwtService nativeJwtService,
            RefreshTokenStore refreshTokenStore,
            SessionStore sessionStore,
            UserAccountStore userAccountStore,
            AuthProperties authProperties
    ) {
        this.nativeJwtService = nativeJwtService;
        this.refreshTokenStore = refreshTokenStore;
        this.sessionStore = sessionStore;
        this.userAccountStore = userAccountStore;
        this.authProperties = authProperties;
    }

    public Optional<UserAccount> resolveAccount(String rawAuthorization) {
        String token = AuthService.extractToken(rawAuthorization);
        if (token.isBlank()) {
            return Optional.empty();
        }

        if (looksLikeJwt(token)) {
            return nativeJwtService.parseAndValidate(token)
                    .flatMap(claims -> userAccountStore.findById(claims.subject()));
        }

        if (!authProperties.isLegacySessionEnabled()) {
            return Optional.empty();
        }

        return sessionStore.resolveUserId(token).flatMap(userAccountStore::findById);
    }

    public Optional<UserAccount> resolveRefreshToken(String refreshToken) {
        return refreshTokenStore.resolveUserId(refreshToken).flatMap(userAccountStore::findById);
    }

    public static boolean looksLikeJwt(String token) {
        return token != null && token.chars().filter(ch -> ch == '.').count() == 2;
    }
}
