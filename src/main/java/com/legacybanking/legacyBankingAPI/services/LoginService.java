package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {
    @Autowired
    private final CustomerService customerService;
    public UserDetails loginUser(CustomerModel customerModel) {
        return customerService.loadUserByUsername(customerModel.getEmail());
    }
}
