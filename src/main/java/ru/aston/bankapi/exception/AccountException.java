package ru.aston.bankapi.exception;

public class AccountException extends RuntimeException{
    public AccountException(String message) {
        super(message);
    }
}
