package com.legacybanking.legacyBankingAPI.models;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionNotification {
    private String email;
    private String type;
    private LocalDateTime localDateTime;
    private Double amount;
    private String receiverEmail;
    private String receiver;
    private String sender;
    private boolean isUserInDatabase;

    public TransactionNotification(String email, String type, LocalDateTime localDateTime, Double amount) {
        this.email = email;
        this.type = type;
        this.localDateTime = localDateTime;
        this.amount = amount;
    }

    public TransactionNotification(String email, String type, LocalDateTime localDateTime, Double amount, String receiver, String sender, boolean isUserInDatabase) {
        this.email = email;
        this.type = type;
        this.localDateTime = localDateTime;
        this.amount = amount;
        this.receiver = receiver;
        this.sender = sender;
        this.isUserInDatabase = isUserInDatabase;
    }
}


