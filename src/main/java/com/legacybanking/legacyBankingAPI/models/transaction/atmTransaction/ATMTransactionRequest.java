package com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction;

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

public class ATMTransactionRequest implements Serializable {
    private static final long serialVersionUID = 563326789L;
    private String cardNumber;
    private String cardVerificationCode;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime expirationDate;
    private CardType type;
    private Long accountPin;
    private Double amount;
    private TransactionType transactionType;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateOfTransaction;
    private String location;
    @JsonIgnore
    private String transactionEnv;
    @JsonIgnore
    private String apiKey;

}
