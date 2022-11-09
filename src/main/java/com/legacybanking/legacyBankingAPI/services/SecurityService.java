package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.SecurityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private CustomerRepo customerRepo;

    public boolean setSecurity(SecurityModel security){
//        Customer customer = customerRepo.findByAccountNumber(security.getAccountNumber());
//        if(security.isLockedCard()){
//            customer.setCardNumber(null);
//            customer.setLocked(security.isLockedCard());
//        }
//        customer.setEnabled(security.isLockedAccount());
//        customerRepo.save(customer);
        return true;
    }
}
