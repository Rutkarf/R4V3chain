package io.r4v3chain.backend.blockchain.application;

import io.r4v3chain.backend.auth.model.UserAccount;
import io.r4v3chain.backend.blockchain.dto.AddPendingTransactionResponse;
import io.r4v3chain.backend.blockchain.dto.CreatePendingTransactionRequest;
import io.r4v3chain.backend.blockchain.dto.MinePendingTransactionResponse;
import io.r4v3chain.backend.blockchain.dto.PendingTransactionResponse;
import io.r4v3chain.backend.blockchain.model.PendingTransaction;

import java.util.List;

public interface PendingTransactionService {

    List<PendingTransactionResponse> getPendingTransactions();

    AddPendingTransactionResponse addPendingTransaction(
            CreatePendingTransactionRequest request,
            UserAccount account
    );

    MinePendingTransactionResponse minePendingTransaction(String id, String minerAddress);

    List<PendingTransaction> getAll();

    boolean addFromPeer(PendingTransaction incoming);

    PendingTransaction findById(String id);

    boolean removeById(String id);
}