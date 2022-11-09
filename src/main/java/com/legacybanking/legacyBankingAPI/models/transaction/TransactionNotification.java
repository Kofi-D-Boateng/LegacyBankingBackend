package com.legacybanking.legacyBankingAPI.models.transaction;

import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionNotification {
    private String email;
    private TransactionType type;
    private LocalDateTime localDateTime;
    private Double amount;
    private String receiverEmail;
    private String receiver;
    private String sender;
    private boolean isReceiverInDatabase;

    public TransactionNotification(String email, TransactionType type, LocalDateTime localDateTime, Double amount) {
        this.email = email;
        this.type = type;
        this.localDateTime = localDateTime;
        this.amount = amount;
    }

    public TransactionNotification(String email, TransactionType type, LocalDateTime localDateTime, Double amount, String receiver, String sender, boolean isReceiverInDatabase) {
        this.email = email;
        this.type = type;
        this.localDateTime = localDateTime;
        this.amount = amount;
        this.receiver = receiver;
        this.sender = sender;
        this.isReceiverInDatabase = isReceiverInDatabase;
    }
}


