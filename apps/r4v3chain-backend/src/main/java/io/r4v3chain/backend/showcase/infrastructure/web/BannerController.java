package io.r4v3chain.backend.showcase.infrastructure.web;

import io.r4v3chain.backend.showcase.dto.BannerResponse;
import io.r4v3chain.backend.blockchain.dto.PendingTransactionResponse;
import io.r4v3chain.backend.blockchain.application.PendingTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BannerController {

    private final PendingTransactionService pendingTransactionService;

    @GetMapping("/banner")
    public BannerResponse getBanner() {
        List<PendingTransactionResponse> pending = pendingTransactionService.getPendingTransactions();

        String lastTransaction = pending.isEmpty()
                ? "Aucune transaction récente"
                : buildLastTransactionText(pending.get(0));

        String lastTransactionShort = pending.isEmpty()
                ? "Aucune"
                : truncate(lastTransaction, 32);

        return new BannerResponse(
                "R4V3chain",
                lastTransaction,
                lastTransactionShort,
                pending.size()
        );
    }

    private String buildLastTransactionText(PendingTransactionResponse tx) {
        return safe(tx.getFromAddress())
                + " -> "
                + safe(tx.getToAddress())
                + " : "
                + (tx.getAmount() != null ? tx.getAmount().stripTrailingZeros().toPlainString() : "0");
    }

    private String truncate(String value, int max) {
        if (value == null || value.length() <= max) {
            return value;
        }
        return value.substring(0, max - 1) + "…";
    }

    private String safe(String value) {
        return value == null ? "?" : value;
    }
}