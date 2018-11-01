package ru.tcezar.blockchain;

import java.security.NoSuchAlgorithmException;

public class BlockchainException extends RuntimeException {

    public BlockchainException() {
    }

    public BlockchainException(String message) {
        super(message);
    }

    public BlockchainException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlockchainException(Throwable cause) {
        super(cause);
    }
}
