package ru.aston.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.aston.bankapi.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a join TransactionHistory t on a.number = t.sender.number or a.number = t.receiver.number")
    List<Account> findAllAccountsWithTransactionHistory();
    @Query("SELECT a FROM Account a join TransactionHistory t on a.number = t.sender.number or a.number = t.receiver.number where a.beneficiaryName = :beneficiaryName")
    List<Account> findAllAccountsByBeneficiaryNameWithTransactionHistory(String beneficiaryName);
    @Query("SELECT a FROM Account a join TransactionHistory t on a.number = t.sender.number or a.number = t.receiver.number where a.number = :number")
    Optional<Account> findAccountsByNumberWithTransactionHistory(int number);
}
