package io.r4v3chain.backend.auth.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r4v3chain.backend.auth.application.AuthException;
import io.r4v3chain.backend.auth.model.UserAccount;
import io.r4v3chain.backend.auth.oauth.store.InMemoryOAuthIdentityStore;
import io.r4v3chain.backend.auth.persistence.JsonUserAccountStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OAuthUserProvisionerTest {

    @TempDir
    Path tempDir;

    private JsonUserAccountStore userStore;
    private OAuthUserProvisioner provisioner;

    @BeforeEach
    void setUp() {
        userStore = new JsonUserAccountStore(
                new ObjectMapper(),
                tempDir.resolve("auth-users.json").toString()
        );
        userStore.loadFromDisk();
        provisioner = new OAuthUserProvisioner(userStore, new InMemoryOAuthIdentityStore());
    }

    @Test
    void createsOAuthAccountWithSanitizedUsername() {
        UserAccount account = provisioner.resolveOrCreate(
                OAuthProvider.GOOGLE,
                "google-subject-1",
                "alice@r4v3chain.dev",
                "Alice Smith"
        );

        assertThat(account.getUsername()).isEqualTo("alice_smith");
        assertThat(account.getEmail()).isEqualTo("alice@r4v3chain.dev");
        assertThat(account.getPasswordHash()).isEqualTo(OAuthUserProvisioner.OAUTH_PASSWORD_SENTINEL);
    }

    @Test
    void reusesExistingIdentityForSameProviderSubject() {
        UserAccount first = provisioner.resolveOrCreate(
                OAuthProvider.GITHUB,
                "gh-42",
                "dev@r4v3chain.dev",
                "Dev User"
        );
        UserAccount second = provisioner.resolveOrCreate(
                OAuthProvider.GITHUB,
                "gh-42",
                "other@r4v3chain.dev",
                "Other"
        );

        assertThat(second.getId()).isEqualTo(first.getId());
    }

    @Test
    void rejectsEmailAlreadyUsedByPasswordAccount() {
        UserAccount passwordAccount = new UserAccount(
                "u1",
                "alice",
                "alice@r4v3chain.dev",
                "$2a$10$hash",
                "",
                System.currentTimeMillis()
        );
        userStore.create(passwordAccount);

        assertThatThrownBy(() -> provisioner.resolveOrCreate(
                OAuthProvider.GOOGLE,
                "google-new",
                "alice@r4v3chain.dev",
                "Alice"
        ))
                .isInstanceOf(AuthException.class)
                .extracting("statusCode")
                .isEqualTo(409);
    }
}
