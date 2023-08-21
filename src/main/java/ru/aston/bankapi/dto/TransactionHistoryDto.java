package ru.aston.bankapi.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aston.bankapi.model.TransactionHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryDto {
    @Schema(description = "ID Of Transaction", example = "105daa2a-106d-4884-b102-a7c0fc6bfb4d")
    private UUID id;
    @Schema(description = "Amount Of Money", example = "199")
    private int amount;
    @Schema(description = "Balance Before Transaction", example = "200")
    private int balanceBefore;
    @Schema(description = "Balance After Transaction", example = "1")
    private int balanceAfter;
    @Schema(description = "Is Transaction Successfully Ended", example = "1")
    private boolean isSuccessful;
    @Schema(description = "Date And Time Of Transaction", example = "2023-08-18 16:58:17.982")
    private LocalDateTime dateTime;
    @Schema(description = "Type Of Transaction", example = "WITHDRAW")
    private String transactionType;
    @Schema(description = "Account Number Of Sender", example = "1000000")
    private Integer senderNumber;
    @Schema(description = "Account Number Of Receiver (for TRANSFER)", example = "1000001")
    private Integer receiverNumber;

    public static TransactionHistoryDto from(TransactionHistory history) {
        return TransactionHistoryDto.builder()
                .id(history.getId())
                .senderNumber(history.getSender() != null ? history.getSender().getNumber() : null)
                .receiverNumber(history.getReceiver() != null ? history.getReceiver().getNumber() : null)
                .amount(history.getAmount())
                .balanceBefore(history.getBalanceBefore())
                .balanceAfter(history.getBalanceAfter())
                .isSuccessful(history.isSuccessful())
                .transactionType(history.getTransactionType().name())
                .dateTime(history.getDateTime())
                .build();
    }

    public static List<TransactionHistoryDto> from(List<TransactionHistory> histories) {
        return histories.stream().map(TransactionHistoryDto::from).toList();
    }
}
