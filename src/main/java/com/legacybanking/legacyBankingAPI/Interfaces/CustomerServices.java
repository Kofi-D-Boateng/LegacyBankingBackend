package com.legacybanking.legacyBankingAPI.Interfaces;

import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import com.legacybanking.legacyBankingAPI.models.customer.Registration;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.VerificationToken;

public interface CustomerServices {
    String registerCustomer(Registration model);
    Customer getCustomerInfo(String email);
    Customer loginUser(String email);
    String generateToken(String email);
    Boolean confirmAccount(VerificationToken token);
    String createCheckingAccount(Registration model);
    String createCreditAccount(Registration model);
    String createSavingsAccount(Registration model);
}
