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
    @Autowired
    private ConfirmationTokenService confirmationTokenService;



    @PostMapping("login")
    public Customer login(@RequestBody Registration registration) throws UsernameNotFoundException {
        return customerService.loginUser(registration.getEmail());

    }

    @PostMapping("token-confirmation")
    public Boolean verifyAccount(@RequestBody @NotNull SecurityModel security){

        if(security.getConfirmationToken().trim().length() <= 0) return false;

        VerificationToken token = confirmationTokenService.retrieveToken(security.getConfirmationToken());

        if(token == null || token.getExpiresAt().isBefore(LocalDateTime.now())) return false;

        return customerService.confirmAccount(token);
    }

    @GetMapping("get-new-token")
    public String generateToken(@RequestParam String email){
        return customerService.generateToken(email);
    }
}
