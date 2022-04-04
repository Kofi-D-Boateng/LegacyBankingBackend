package com.legacybanking.legacyBankingAPI.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {
    private Double amount;
    private Long cardNumber;
    private String accountNumber;
    private Long phoneNumberOfTransferee;
    private String emailOfTransferee;
    private int cvc;
    private String location;
    private String zipcode;
    private String type;
    private LocalDate expirationDate;
    private LocalDate dateOfTransaction;
    private Boolean isTransferringToOutsideBank;
    private Boolean isRefund;
}
