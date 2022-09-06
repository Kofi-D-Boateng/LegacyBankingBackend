package com.legacybanking.legacyBankingAPI.controller;

import com.legacybanking.legacyBankingAPI.models.Customer;
import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import com.legacybanking.legacyBankingAPI.models.SecurityModel;
import com.legacybanking.legacyBankingAPI.services.CustomerService;
import com.legacybanking.legacyBankingAPI.services.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/authentication/")
@AllArgsConstructor
@Slf4j
public class AuthController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RegistrationService registrationService;

    @PostMapping("registration")
    public String register(@RequestBody CustomerModel customerModel){
        return registrationService.register(customerModel);
    }

    @PostMapping("/login")
    public Customer login(@RequestBody CustomerModel customerModel) throws UsernameNotFoundException {
        return customerService.loginUser(customerModel.getEmail());

    }

    @PostMapping("/token-confirmation")
    public boolean verifyAccount(@RequestBody @NotNull SecurityModel security){
        log.info("SECURITY CHECK: {}",security.getConfirmationToken());
        return registrationService.verifyAccount(security);
    }

    @GetMapping("/get-new-token")
    public String generateToken(@RequestParam String email){
        return registrationService.generateToken(email);
    }
}
