package ru.aston.bankapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.aston.bankapi.dto.*;
import ru.aston.bankapi.enums.TransactionType;
import ru.aston.bankapi.exception.AccountException;
import ru.aston.bankapi.exception.BalanceException;
import ru.aston.bankapi.exception.PinCodeException;
import ru.aston.bankapi.model.Account;
import ru.aston.bankapi.model.TransactionHistory;
import ru.aston.bankapi.repository.AccountRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionHistoryService transactionHistoryService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Account createAccount(CreateAccountDto dto) {
        Account account = Account.builder()
                .beneficiaryName(dto.getBeneficiaryName())
                .pinCodeHash(passwordEncoder.encode(dto.getPinCode().toString()))
                .isActive(true)
                .build();
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountByNumber(int number) {
        return accountRepository.findAccountsByNumberWithTransactionHistory(number).orElseThrow(
                (Supplier<RuntimeException>) () -> new AccountException("Указанный счет не найден"));
    }

    @Override
    public List<Account> getAllAccountsWithHistory(String beneficiaryName) {
        return beneficiaryName != null ?
                accountRepository.findAllAccountsByBeneficiaryNameWithTransactionHistory(beneficiaryName) : accountRepository.findAllAccountsWithTransactionHistory();
    }

    @Transactional
    @Override
    public Account deposit(DepositDto dto) {
        Account receiver = accountRepository.findById(dto.getNumber()).orElseThrow(
                (Supplier<RuntimeException>) () -> new AccountException("Указанный счет не найден"));
        if (!receiver.isActive()) {
            throw new AccountException("Указанный счет неактиваен");
        }

        TransactionHistory history = TransactionHistory.builder()
                .receiver(receiver)
                .amount(dto.getAmount())
                .balanceBefore(receiver.getBalance())
                .balanceAfter(receiver.getBalance() + dto.getAmount())
                .isSuccessful(true)
                .transactionType(TransactionType.DEPOSIT)
                .dateTime(LocalDateTime.now())
                .build();
        transactionHistoryService.addHistory(history);

        addBalance(receiver, dto.getAmount());
        return accountRepository.save(receiver);
    }

    @Transactional
    @Override
    public Account withdraw(WithdrawDto dto) {
        Account sender = accountRepository.findById(dto.getNumber()).orElseThrow(
                (Supplier<RuntimeException>) () -> new AccountException("Указанный счет не найден"));
        if (!passwordEncoder.matches(String.valueOf(dto.getPinCode()), sender.getPinCodeHash())) {
            throw new PinCodeException("Неверный PIN-код");
        }
        if (!sender.isActive()) {
            throw new AccountException("Указанный счет неактиваен");
        }

        TransactionHistory history = TransactionHistory.builder()
                .sender(sender)
                .amount(dto.getAmount())
                .balanceBefore(sender.getBalance())
                .balanceAfter(sender.getBalance() - dto.getAmount())
                .isSuccessful(true)
                .transactionType(TransactionType.WITHDRAW)
                .dateTime(LocalDateTime.now())
                .build();
        transactionHistoryService.addHistory(history);

        subtractBalance(sender, dto.getAmount());
        return accountRepository.save(sender);
    }

    @Transactional
    @Override
    public Account transfer(TransferDto dto) {
        Account sender = accountRepository.findById(dto.getSenderNumber()).orElseThrow(
                (Supplier<RuntimeException>) () -> new AccountException("Cчет отправителя не найден"));
        if (!passwordEncoder.matches(String.valueOf(dto.getPinCode()), sender.getPinCodeHash())) {
            throw new PinCodeException("Неверный PIN-код");
        }
        if (!sender.isActive()) {
            throw new AccountException("Cчет отправителя неактиваен");
        }
        Account receiver = accountRepository.findById(dto.getReceiverNumber()).orElseThrow(
                (Supplier<RuntimeException>) () -> new AccountException("Cчет получателя не найден"));
        if (!receiver.isActive()) {
            throw new AccountException("Cчет получателя неактиваен");
        }
        TransactionHistory history = TransactionHistory.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(dto.getAmount())
                .balanceBefore(sender.getBalance())
                .balanceAfter(sender.getBalance() - dto.getAmount())
                .isSuccessful(true)
                .transactionType(TransactionType.TRANSFER)
                .dateTime(LocalDateTime.now())
                .build();
        transactionHistoryService.addHistory(history);


        addBalance(receiver, dto.getAmount());
        accountRepository.save(receiver);

        subtractBalance(sender, dto.getAmount());
        return accountRepository.save(sender);
    }

    private void addBalance(Account account, int value) {
        account.setBalance(account.getBalance() + value);
    }

    private void subtractBalance(Account account, int value) {
        if (value > account.getBalance()) {
            throw new BalanceException("Недостаточно средств");
        }
        account.setBalance(account.getBalance() - value);
    }
}
