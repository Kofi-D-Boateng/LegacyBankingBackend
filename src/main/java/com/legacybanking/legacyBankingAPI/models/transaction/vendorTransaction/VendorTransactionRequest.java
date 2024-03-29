package com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import com.legacybanking.legacyBankingAPI.utils.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class VendorTransactionRequest implements Serializable {
    private static final long serialVersionUID = 723326789L;
    private String cardNumber;
    private String cardVerificationCode;
    private String cardHolderName;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expirationDate;
    private CardType type;
    private String merchantName;
    private String merchantDescription;
    private Double amount;
    private TransactionType transactionType;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateOfTransaction;
    private String location;
    @JsonIgnore
    private String apiKey;
    @JsonIgnore
    private String transactionEnv;


}
