package com.legacybanking.legacyBankingAPI.controller;

import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import com.legacybanking.legacyBankingAPI.services.CustomerService;
import com.legacybanking.legacyBankingAPI.services.LoginService;
import com.legacybanking.legacyBankingAPI.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/customer/")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;
    private RegistrationService registrationService;
    private LoginService loginService;


    @PostMapping("registration")
    public boolean register(@RequestBody CustomerModel customerModel){
        return registrationService.register(customerModel);
    }

    @PostMapping("login")
    public UserDetails login(@RequestBody CustomerModel customerModel){
        return loginService.loginUser(customerModel);
    }

}
