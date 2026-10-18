package io.r4v3chain.backend.chain;

/**
 * Schéma d'adresse R4V3chain — legacy P-256 ou EVM-compatible (Keccak/secp256k1).
 */
public enum AddressScheme {
    LEGACY,
    EVM;

    public static AddressScheme fromAddress(String address) {
        if (address != null && address.startsWith("0x") && address.length() == 42) {
            return EVM;
        }
        return LEGACY;
    }
}
