package com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction;

import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class VendorTransactionRequest {
    private String cardNumber;
    private String cardVerificationCode;
    private String cardHolderName;
    private LocalDateTime expirationDate;
    private CardType type;
    private String merchantName;
    private String merchantDescription;
    private Double amount;
    private TransactionType transactionType;
    private LocalDateTime dateOfTransaction;
    private String location;

}
