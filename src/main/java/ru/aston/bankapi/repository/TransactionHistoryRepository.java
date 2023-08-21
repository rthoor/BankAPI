package ru.aston.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aston.bankapi.model.TransactionHistory;

import java.util.List;
import java.util.UUID;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, UUID> {
    @Query("SELECT t FROM TransactionHistory t where  t.sender.number = :accountNumber or t.receiver.number = :accountNumber")
    List<TransactionHistory> findTransactionHistoriesByAccountNumber(int accountNumber);
}
