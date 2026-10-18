package io.r4v3chain.backend.shared.exception;

public class InvalidBlockException extends RuntimeException {

    public InvalidBlockException(String message) {
        super(message);
    }
}