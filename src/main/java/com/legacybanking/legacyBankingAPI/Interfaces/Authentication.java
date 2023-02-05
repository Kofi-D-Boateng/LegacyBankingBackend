package com.legacybanking.legacyBankingAPI.Interfaces;

import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.models.user.Registration;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.SecurityModel;

public interface Authentication{
    Customer login(Registration model);
    Boolean verifyAccount(SecurityModel model);
    String generateToken(String email);
}
