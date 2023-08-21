package ru.aston.bankapi.exception;

public class BalanceException extends RuntimeException{
    public BalanceException(String message) {
        super(message);
    }
}

