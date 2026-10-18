package io.r4v3chain.backend.blockchain.infrastructure.web;

import io.r4v3chain.backend.blockchain.dto.BlockValidationResult;
import io.r4v3chain.backend.blockchain.dto.CreateBlockRequest;
import io.r4v3chain.backend.blockchain.model.Block;
import io.r4v3chain.backend.p2p.P2pService;
import io.r4v3chain.backend.blockchain.application.BlockchainService;
import io.r4v3chain.backend.blockchain.application.BlockchainValidationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blocks")
public class BlockController {

    private final BlockchainService blockchainService;
    private final BlockchainValidationService validationService;
    private final P2pService p2pService;

    public BlockController(
            BlockchainService blockchainService,
            BlockchainValidationService validationService,
            P2pService p2pService
    ) {
        this.blockchainService = blockchainService;
        this.validationService = validationService;
        this.p2pService = p2pService;
    }

    @GetMapping
    public ResponseEntity<List<Block>> getBlocks() {
        return ResponseEntity.ok(blockchainService.getBlocks());
    }

    @GetMapping("/latest")
    public ResponseEntity<Block> getLatestBlock() {
        return ResponseEntity.ok(blockchainService.getLatestBlock());
    }

    @PostMapping
    public ResponseEntity<Block> createBlock(@Valid @RequestBody CreateBlockRequest request) {
        Block createdBlock = blockchainService.addBlock(request.getData());
        p2pService.broadcastLatest();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlock);
    }

    @GetMapping("/validate")
    public ResponseEntity<BlockValidationResult> validateChain() {
        return ResponseEntity.ok(
                validationService.validateChain(blockchainService.getBlocks())
        );
    }

    @PostMapping("/validate")
    public ResponseEntity<BlockValidationResult> validateIncomingBlock(@RequestBody Block block) {
        return ResponseEntity.ok(
                validationService.validateBlockAgainstChain(block, blockchainService.getBlocks())
        );
    }
}