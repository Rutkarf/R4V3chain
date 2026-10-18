package io.r4v3chain.backend.exchange.infrastructure.web;

import io.r4v3chain.backend.auth.security.RoleAuthorizationService;
import io.r4v3chain.backend.exchange.dto.ExchangePanelResponse;
import io.r4v3chain.backend.exchange.dto.ExchangeSwapRequest;
import io.r4v3chain.backend.exchange.dto.ExchangeSwapResponse;
import io.r4v3chain.backend.exchange.application.ExchangeService;
import io.r4v3chain.backend.wallet.dto.WalletPortfolioResponse;
import io.r4v3chain.backend.web.RequestClientInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchange-panel")
public class ExchangeController {

    private final ExchangeService exchangeService;
    private final RoleAuthorizationService roleAuthorizationService;

    public ExchangeController(
            ExchangeService exchangeService,
            RoleAuthorizationService roleAuthorizationService
    ) {
        this.exchangeService = exchangeService;
        this.roleAuthorizationService = roleAuthorizationService;
    }

    @GetMapping
    public ExchangePanelResponse getExchangePanel(
            @RequestParam(required = false) String walletAddress,
            @RequestParam(required = false) String fromToken,
            @RequestParam(required = false) String toToken
    ) {
        return exchangeService.getPanel(walletAddress, fromToken, toToken);
    }

    /** Soldes R4V3 + tokens swap persistés (snapshot wallet_balances). */
    @GetMapping("/portfolio")
    public WalletPortfolioResponse getPortfolio(
            @RequestParam String walletAddress
    ) {
        return exchangeService.getPortfolio(walletAddress);
    }

    @PostMapping("/swap")
    public ExchangeSwapResponse swap(
            @RequestBody ExchangeSwapRequest request,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            HttpServletRequest httpRequest
    ) {
        String detail = request != null ? request.walletAddress() : null;
        String ip = RequestClientInfo.clientIp(httpRequest);
        roleAuthorizationService.authorizeMutation(authorization, "exchange.swap", detail, ip);
        return exchangeService.swap(request, authorization);
    }
}
