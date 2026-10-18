package io.r4v3chain.backend.explorer.dto;

import io.r4v3chain.backend.blockchain.model.Block;

import java.util.List;

public record ExplorerBlocksResponse(
        String wallet,
        Integer fromIndex,
        Integer toIndex,
        int total,
        List<Block> blocks
) {
}
