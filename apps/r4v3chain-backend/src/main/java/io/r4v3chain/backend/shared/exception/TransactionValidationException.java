package io.r4v3chain.backend.shared.exception;

public class TransactionValidationException extends RuntimeException {

    public TransactionValidationException(String message) {
        super(message);
    }
}