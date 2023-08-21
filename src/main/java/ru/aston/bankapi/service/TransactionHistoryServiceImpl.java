package ru.aston.bankapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aston.bankapi.model.TransactionHistory;
import ru.aston.bankapi.repository.TransactionHistoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService{

    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public TransactionHistory addHistory(TransactionHistory transactionHistory) {
        return transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public List<TransactionHistory> getHistoryByAccountNumber(int accountNumber) {
        return transactionHistoryRepository.findTransactionHistoriesByAccountNumber(accountNumber);
    }
}
