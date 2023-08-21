package ru.aston.bankapi.service;

import ru.aston.bankapi.dto.CreateAccountDto;
import ru.aston.bankapi.dto.DepositDto;
import ru.aston.bankapi.dto.TransferDto;
import ru.aston.bankapi.dto.WithdrawDto;
import ru.aston.bankapi.model.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(CreateAccountDto dto);
    Account getAccountByNumber(int number);
    List<Account> getAllAccountsWithHistory(String beneficiaryName);
    Account deposit(DepositDto dto);
    Account withdraw(WithdrawDto dto);
    Account transfer(TransferDto dto);
}
