package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.models.Customer;
import com.legacybanking.legacyBankingAPI.models.SecurityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    @Autowired
    final CustomerRepo customerRepo;

    public boolean setSecurity(SecurityModel security){
        Customer customer = customerRepo.findByAccountNumber(security.getAccountNumber());
        if(security.isLockedCard()){
            customer.setCardNumber(null);
            customer.setLocked(security.isLockedCard());
            customerRepo.save(customer);
            return true;
        }
        if(security.isLockedAccount()){
            customer.setEnabled(false);
            customerRepo.save(customer);
            return true;
        }
        return false;
    }
}
