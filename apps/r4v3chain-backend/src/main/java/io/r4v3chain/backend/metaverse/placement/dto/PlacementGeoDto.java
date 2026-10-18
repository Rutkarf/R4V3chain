package io.r4v3chain.backend.metaverse.placement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PlacementGeoDto(
        double latitude,
        double longitude,
        Double altitude,
        String source
) {
}
