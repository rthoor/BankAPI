package ru.aston.bankapi.exception;

public class PinCodeException extends RuntimeException{
    public PinCodeException(String message) {
        super(message);
    }
}

