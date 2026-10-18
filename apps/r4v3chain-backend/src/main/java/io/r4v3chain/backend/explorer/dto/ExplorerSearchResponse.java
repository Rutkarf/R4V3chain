package io.r4v3chain.backend.explorer.dto;

import java.util.List;

public record ExplorerSearchResponse(
        String query,
        List<ExplorerSearchResultDto> results
) {
}
