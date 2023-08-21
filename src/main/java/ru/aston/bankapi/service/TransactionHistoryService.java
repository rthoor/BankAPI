package ru.aston.bankapi.service;

import ru.aston.bankapi.model.TransactionHistory;

import java.util.List;

public interface TransactionHistoryService {
    TransactionHistory addHistory(TransactionHistory transactionHistory);

    List<TransactionHistory> getHistoryByAccountNumber(int accountNumber);
}
