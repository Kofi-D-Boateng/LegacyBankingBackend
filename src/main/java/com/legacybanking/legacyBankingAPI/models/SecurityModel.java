package com.legacybanking.legacyBankingAPI.models;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityModel {
    private String confirmationToken;
    private boolean lockedCard;
    private boolean lockedAccount;
    private String accountNumber;
}
