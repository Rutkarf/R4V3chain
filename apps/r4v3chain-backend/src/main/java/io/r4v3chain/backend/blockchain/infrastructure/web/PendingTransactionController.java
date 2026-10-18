package io.r4v3chain.backend.blockchain.infrastructure.web;

import io.r4v3chain.backend.auth.application.AuthService;
import io.r4v3chain.backend.auth.security.RoleAuthorizationService;
import io.r4v3chain.backend.blockchain.dto.AddPendingTransactionResponse;
import io.r4v3chain.backend.blockchain.dto.CreatePendingTransactionRequest;
import io.r4v3chain.backend.blockchain.dto.MinePendingTransactionResponse;
import io.r4v3chain.backend.blockchain.dto.MineRequest;
import io.r4v3chain.backend.blockchain.dto.PendingTransactionResponse;
import io.r4v3chain.backend.blockchain.application.PendingTransactionService;
import io.r4v3chain.backend.web.RequestClientInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PendingTransactionController {

    private final PendingTransactionService pendingTransactionService;
    private final AuthService authService;
    private final RoleAuthorizationService roleAuthorizationService;

    @GetMapping("/pending-transactions")
    public List<PendingTransactionResponse> getPendingTransactions() {
        return pendingTransactionService.getPendingTransactions();
    }

    @PostMapping("/pending-transactions")
    public AddPendingTransactionResponse addPendingTransaction(
            @Valid @RequestBody CreatePendingTransactionRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            HttpServletRequest httpRequest
    ) {
        String ip = RequestClientInfo.clientIp(httpRequest);
        var account = roleAuthorizationService.authorizeMutation(
                authorization,
                "pending.create",
                request.getFromAddress(),
                ip
        );
        authService.ensureWalletOwnership(account, request.getFromAddress());
        return pendingTransactionService.addPendingTransaction(request, account);
    }

    @PostMapping("/pending-transactions/{id}/mine")
    public MinePendingTransactionResponse minePendingTransaction(
            @PathVariable String id,
            @RequestBody(required = false) MineRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            HttpServletRequest httpRequest
    ) {
        String ip = RequestClientInfo.clientIp(httpRequest);
        var account = roleAuthorizationService.authorizeMutation(authorization, "pending.mine", id, ip);
        String minerAddress = request != null && request.getMinerAddress() != null
                && !request.getMinerAddress().isBlank()
                ? request.getMinerAddress()
                : account.getWalletAddress();
        authService.ensureWalletOwnership(account, minerAddress);
        return pendingTransactionService.minePendingTransaction(id, minerAddress);
    }
}
