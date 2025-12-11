package com.npci.gateway.exception;

public class InvalidUpiIdException extends RuntimeException {
    public InvalidUpiIdException(String message) {
        super(message);
    }
}
