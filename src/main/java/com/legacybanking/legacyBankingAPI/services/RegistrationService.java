package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.CustomerRole;
import com.legacybanking.legacyBankingAPI.models.Customer;
import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final CustomerService customerService;

    public boolean register(CustomerModel customerModel) {
        String validEmail = customerModel.getEmail();
        if(validEmail == null){
            return false;
        }else{
            return customerService.signUpCustomer(customerModel);
        }
    }
}
