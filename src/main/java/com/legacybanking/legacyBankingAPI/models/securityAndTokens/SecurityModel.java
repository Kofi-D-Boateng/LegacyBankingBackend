package com.legacybanking.legacyBankingAPI.models.securityAndTokens;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityModel {
    private String confirmationToken;
    private String accountNumber;
    private String cardNumber;
    private String requestType;
    private String email;
    @JsonIgnore
    private String apiKey;
}
