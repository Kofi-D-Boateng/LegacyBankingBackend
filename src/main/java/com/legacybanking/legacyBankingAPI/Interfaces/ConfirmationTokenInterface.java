package com.legacybanking.legacyBankingAPI.Interfaces;

import com.legacybanking.legacyBankingAPI.models.securityAndTokens.VerificationToken;

public interface ConfirmationTokenInterface {
    VerificationToken retrieveToken(String token);
    void removeTokenFromDB(VerificationToken token);
}
