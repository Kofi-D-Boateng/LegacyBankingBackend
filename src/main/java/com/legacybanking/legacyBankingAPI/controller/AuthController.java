package com.legacybanking.legacyBankingAPI.controller;

import com.legacybanking.legacyBankingAPI.Interfaces.Authentication;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.models.user.Registration;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.SecurityModel;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.VerificationToken;
import com.legacybanking.legacyBankingAPI.services.ConfirmationTokenService;
import com.legacybanking.legacyBankingAPI.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api/v2/authentication/")
@AllArgsConstructor
@Slf4j
public class AuthController implements Authentication {
    @Autowired
    private CustomerService customerService;

    @PostMapping("login")
    public Customer login(@RequestBody Registration registration) throws UsernameNotFoundException {
        return customerService.loginUser(registration.getEmail());

    }
}
