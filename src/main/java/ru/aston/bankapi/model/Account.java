package ru.aston.bankapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account {
    @Id
    @SequenceGenerator(initialValue = 1000000, allocationSize = 1,
            name = "number_sequence", sequenceName="number_sequence")
    @GeneratedValue(generator="number_sequence")
    @Column(name = "number", unique = true, nullable = false, insertable = false, updatable = false)
    private int number;

    private String beneficiaryName;

    private String pinCodeHash;

    private int balance;

    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="sender", fetch = FetchType.LAZY)
    private List<TransactionHistory> senderTransactionHistory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="receiver", fetch = FetchType.LAZY)
    private List<TransactionHistory> receiverTransactionHistory;
}
