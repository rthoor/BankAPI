package ru.aston.bankapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aston.bankapi.model.Account;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Account")
public class AccountDto {
    @Schema(description = "Account Number", example = "1000003")
    private Integer number;
    @Schema(description = "Beneficiary Name", example = "Name Surname")
    private String beneficiaryName;
    @Schema(description = "Current Balance", example = "200")
    private int balance;
    @Schema(description = "Is Account Active", example = "true")
    private boolean isActive;
    @Schema(description = "Transaction History")
    private List<TransactionHistoryDto> transactionHistory;

    public static AccountDto from(Account account) {
        return AccountDto.builder()
                .number(account.getNumber())
                .beneficiaryName(account.getBeneficiaryName())
                .balance(account.getBalance())
                .isActive(account.isActive())
                .transactionHistory(getTransactionHistory(account))
                .build();
    }

    public static List<AccountDto> from(List<Account> accounts) {
        return accounts.stream().map(AccountDto::from).toList();
    }

    private static List<TransactionHistoryDto> getTransactionHistory(Account account) {
        List<TransactionHistoryDto> transactionHistory = new ArrayList<>();
        if (account.getSenderTransactionHistory() != null) {
            transactionHistory.addAll(account.getSenderTransactionHistory().stream().map(TransactionHistoryDto::from).toList());
        }
        if (account.getReceiverTransactionHistory() != null) {
            transactionHistory.addAll(account.getReceiverTransactionHistory().stream().map(TransactionHistoryDto::from).toList());
        }
        return transactionHistory;
    }
}
