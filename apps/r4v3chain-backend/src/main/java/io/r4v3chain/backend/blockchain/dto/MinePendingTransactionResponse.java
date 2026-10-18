package io.r4v3chain.backend.blockchain.dto;

import io.r4v3chain.backend.blockchain.model.Block;

public class MinePendingTransactionResponse {

    private String message;
    private Block block;

    public MinePendingTransactionResponse() {
    }

    public MinePendingTransactionResponse(String message, Block block) {
        this.message = message;
        this.block = block;
    }

    public String getMessage() {
        return message;
    }

    public Block getBlock() {
        return block;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}