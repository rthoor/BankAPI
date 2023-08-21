package ru.aston.bankapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.aston.bankapi.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int amount;

    private int balanceBefore;

    private int balanceAfter;

    private boolean isSuccessful;

    private LocalDateTime dateTime;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "sender_number")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_number")
    private Account receiver;
}
