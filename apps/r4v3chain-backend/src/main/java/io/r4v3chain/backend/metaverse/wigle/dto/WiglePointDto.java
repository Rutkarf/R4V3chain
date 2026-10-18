package io.r4v3chain.backend.metaverse.wigle.dto;

public record WiglePointDto(
        String id,
        double latitude,
        double longitude,
        String networkName,
        String networkType,
        int signalStrength,
        String source
) {
}
