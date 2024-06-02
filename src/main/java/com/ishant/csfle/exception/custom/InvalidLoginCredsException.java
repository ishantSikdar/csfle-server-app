package com.ishant.csfle.exception.custom;

public class InvalidLoginCredsException extends RuntimeException {
    public InvalidLoginCredsException(String message) {
        super(message);
    }
}
