package io.r4v3chain.backend.wallet.application;

import io.r4v3chain.backend.wallet.dto.WalletPublicView;
import io.r4v3chain.backend.wallet.dto.WalletVerifyResponse;
import io.r4v3chain.backend.wallet.model.Wallet;
import io.r4v3chain.backend.blockchain.model.Transaction;

import java.math.BigDecimal;

public interface WalletService {

    Transaction createSignedTransaction(
            String senderAddress,
            String senderPublicKey,
            String senderPrivateKey,
            String recipientAddress,
            BigDecimal amount,
            String memo
    );

    Transaction createVerifiedTransaction(
            String senderAddress,
            String senderPublicKey,
            String recipientAddress,
            BigDecimal amount,
            String memo,
            long timestamp,
            String payload,
            String signature
    );

    WalletPublicView toPublicView(Wallet wallet);

    WalletVerifyResponse verifyWallet(String address, String publicKeyBase64);
}