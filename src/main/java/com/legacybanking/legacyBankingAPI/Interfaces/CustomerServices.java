package com.legacybanking.legacyBankingAPI.Interfaces;

import com.legacybanking.legacyBankingAPI.models.EmailAttributes;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.models.user.Registration;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.VerificationToken;

public interface CustomerServices {
    EmailAttributes registerCustomer(Registration model);
    Customer getCustomerInfo(String email);
    Customer loginUser(String email);
    String generateToken(String email);
    Boolean confirmAccount(VerificationToken token);
    String createCheckingAccount(Registration model);
    String createCreditAccount(Registration model);
    String createSavingsAccount(Registration model);
}
