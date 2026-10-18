package io.r4v3chain.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductionSecretPolicyTest {

    @Test
    void acceptsStrongSecrets() {
        MockEnvironment environment = strongEnvironment();

        assertThatCode(() -> ProductionSecretPolicy.validate(environment))
                .doesNotThrowAnyException();
    }

    @Test
    void rejectsWeakDatabasePassword() {
        MockEnvironment environment = strongEnvironment();
        environment.setProperty("DATABASE_PASSWORD", "r4v3chain");

        assertThatThrownBy(() -> ProductionSecretPolicy.validate(environment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("DATABASE_PASSWORD");
    }

    @Test
    void rejectsShortJwtSecret() {
        MockEnvironment environment = strongEnvironment();
        environment.setProperty("r4v3chain.auth.jwt-secret", "too-short");

        assertThatThrownBy(() -> ProductionSecretPolicy.validate(environment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("R4V3CHAIN_JWT_SECRET");
    }

    @Test
    void rejectsShippedAdminSeed() {
        MockEnvironment environment = strongEnvironment();
        environment.setProperty(
                "r4v3chain.admin.seed-sha256",
                ProductionSecretPolicy.SHIPPED_ADMIN_SEED_SHA256
        );

        assertThatThrownBy(() -> ProductionSecretPolicy.validate(environment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("R4V3CHAIN_ADMIN_SEED_SHA256")
                .hasMessageNotContaining(ProductionSecretPolicy.SHIPPED_ADMIN_SEED_SHA256);
    }

    @Test
    void rejectsDockerDefaultActuatorToken() {
        MockEnvironment environment = strongEnvironment();
        environment.setProperty("r4v3chain.ops.actuator-token", "local-docker-actuator-token");

        assertThatThrownBy(() -> ProductionSecretPolicy.validate(environment))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("R4V3CHAIN_ACTUATOR_TOKEN");
    }

    private static MockEnvironment strongEnvironment() {
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty(
                "r4v3chain.auth.jwt-secret",
                "integration-test-jwt-secret-value-32chars-min"
        );
        environment.setProperty("r4v3chain.ops.actuator-token", "integration-test-actuator-token-ah");
        environment.setProperty("DATABASE_PASSWORD", "integration-test-db-password-ah");
        return environment;
    }
}
