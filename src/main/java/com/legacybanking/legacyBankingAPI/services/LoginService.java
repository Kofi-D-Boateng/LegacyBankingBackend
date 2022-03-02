package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.models.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LoginService {
    private final CustomerService customerService;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetails login(LoginRequest request){
        boolean isValid = emailValidator.test((request.getUsername()));
        if(!isValid){
            throw new IllegalStateException("Invalid email or password");
        }
        return customerService.loadUserByUsername(request.getUsername());

    }

}
