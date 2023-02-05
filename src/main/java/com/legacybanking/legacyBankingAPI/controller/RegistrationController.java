package com.legacybanking.legacyBankingAPI.controller;

import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.models.user.Registration;
import com.legacybanking.legacyBankingAPI.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v2/admin/")
@AllArgsConstructor
@Slf4j
public class RegistrationController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("register-new-customer")
    public String register(@RequestBody Registration model){
        log.info(String.valueOf(model));
        return customerService.registerCustomer(model);
    }

    @PostMapping("registration")
    public String createCustomerAccountAndCard(@RequestBody Registration model){
        log.info("Model: {}",model);
        if(model.getBankAccountType().equals(BankAccountType.CREDIT)) return customerService.createCreditAccount(model);
        else if(model.getBankAccountType().equals(BankAccountType.CHECKING)) return customerService.createCheckingAccount(model);
        else if(model.getBankAccountType().equals(BankAccountType.SAVINGS)) return customerService.createSavingsAccount(model);
        else if(model.getBankAccountType().equals(BankAccountType.CRYPTO)) return "";
        return null;
    }
}
