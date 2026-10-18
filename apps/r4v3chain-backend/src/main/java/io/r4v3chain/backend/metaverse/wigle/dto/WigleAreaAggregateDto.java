package io.r4v3chain.backend.metaverse.wigle.dto;

import java.util.Map;

public record WigleAreaAggregateDto(
        String areaId,
        double latitudeApprox,
        double longitudeApprox,
        int observationCount,
        Double signalAverage,
        Map<String, Integer> networkTypeCounts,
        String confidence,
        String source
) {
}
