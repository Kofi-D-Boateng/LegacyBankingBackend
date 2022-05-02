package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.CustomerRole;
import com.legacybanking.legacyBankingAPI.models.Customer;
import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import com.legacybanking.legacyBankingAPI.models.SecurityModel;
import com.legacybanking.legacyBankingAPI.models.VerificationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final CustomerService customerService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(CustomerModel customerModel) {
        String validEmail = customerModel.getEmail();
        if(validEmail == null){
            return "";
        }else{
            return customerService.signUpCustomer(customerModel);
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
}
