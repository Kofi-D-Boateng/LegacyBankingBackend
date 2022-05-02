package com.legacybanking.legacyBankingAPI.Interfaces;

import com.legacybanking.legacyBankingAPI.models.VerificationToken;

public interface ConfirmationTokenInterface {
    void saveToken(VerificationToken token);
    VerificationToken retrieveToken(String token);
    void removeTokenFromDB(String token);
}
