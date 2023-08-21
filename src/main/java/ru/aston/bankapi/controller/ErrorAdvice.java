package ru.aston.bankapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.aston.bankapi.dto.ErrorDto;
import ru.aston.bankapi.exception.AccountException;
import ru.aston.bankapi.exception.BalanceException;
import ru.aston.bankapi.exception.PinCodeException;

@ControllerAdvice
public class ErrorAdvice {

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorDto> handleException(AccountException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<ErrorDto> handleException(BalanceException e) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(PinCodeException.class)
    public ResponseEntity<ErrorDto> handleException(PinCodeException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(e.getMessage()));
    }

}