package io.r4v3chain.backend.auth.security;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class TotpAuthenticatorTest {

    @Test
    void matchesRfc6238Sha1Vector() {
        byte[] key = "12345678901234567890".getBytes(StandardCharsets.US_ASCII);
        assertThat(TotpAuthenticator.codeAt(key, 59, 8)).isEqualTo("94287082");
    }

    @Test
    void acceptsCurrentCodeAndRejectsGarbage() {
        String secret = TotpAuthenticator.generateSecret();
        assertThat(TotpAuthenticator.matches(secret, TotpAuthenticator.currentCode(secret))).isTrue();
        assertThat(TotpAuthenticator.matches(secret, "000000")).isFalse();
    }

    @Test
    void otpauthUrlCarriesSecret() {
        String url = TotpAuthenticator.otpauthUrl("ada@r4v3chain.review", "JBSWY3DPEHPK3PXP");
        assertThat(url).startsWith("otpauth://totp/R4v3chain:");
        assertThat(url).contains("secret=JBSWY3DPEHPK3PXP");
        assertThat(url).contains("issuer=R4v3chain");
    }
}
