package com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction;

import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ATMTransactionRequest {
    private String cardNumber;
    private String cardVerificationCode;
    private LocalDateTime expirationDate;
    private CardType type;
    private Long accountPin;
    private Double amount;
    private TransactionType transactionType;
    private LocalDateTime dateOfTransaction;
    private String location;

}
