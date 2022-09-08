package com.legacybanking.legacyBankingAPI.services;


import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import com.legacybanking.legacyBankingAPI.models.SecurityModel;
import com.legacybanking.legacyBankingAPI.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private  CustomerService customerService;
    @Autowired
    private  ConfirmationTokenService confirmationTokenService;

    public String register(CustomerModel customer) {

        if(customer.getEmail().trim().length() == 0 || customer.getPassword().trim().length() == 0){
            return "";
        }else{
            return customerService.signUpCustomer(customer);
        }
    }

    public boolean verifyAccount(SecurityModel security) {
        VerificationToken VT  = confirmationTokenService.retrieveToken(security.getConfirmationToken());
        if(VT == null){
            return false;
        }
        if(VT.getConfirmedAt() != null){
            return false;
        }
        LocalDateTime expiredAt = VT.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            return false;
        }

        return customerService.confirmAccount(VT);
    }

    public String generateToken(String email) {
        return customerService.generateToken(email);
    }
}
