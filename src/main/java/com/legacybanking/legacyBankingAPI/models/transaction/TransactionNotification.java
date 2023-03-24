package com.legacybanking.legacyBankingAPI.models.transaction;

import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionNotification implements Serializable {
    @Serial
    private static final long serialVersionUID = 90216789L;
    private String email;
    private TransactionType type;
    private LocalDateTime dateOfTransaction;
    private Double amount;
    private String receiverEmail;
    private String receiver;
    private String sender;
    private boolean isReceiverInDatabase;

    public TransactionNotification(String email, TransactionType type, LocalDateTime dateOfTransaction, Double amount) {
        this.email = email;
        this.type = type;
        this.dateOfTransaction = dateOfTransaction;
        this.amount = amount;
    }

    public TransactionNotification(String email, TransactionType type, LocalDateTime dateOfTransaction, Double amount, String receiver, String sender, boolean isReceiverInDatabase) {
        this.email = email;
        this.type = type;
        this.dateOfTransaction = dateOfTransaction;
        this.amount = amount;
        this.receiver = receiver;
        this.sender = sender;
        this.isReceiverInDatabase = isReceiverInDatabase;
    }
}


