package com.legacybanking.legacyBankingAPI.controller;

import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import com.legacybanking.legacyBankingAPI.services.LoginService;
import com.legacybanking.legacyBankingAPI.services.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth/")
@AllArgsConstructor
@Slf4j
public class AuthController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RegistrationService registrationService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("registration")
    public boolean register(@RequestBody CustomerModel customerModel){
        return registrationService.register(customerModel);
    }

    @PostMapping("login")
    public UserDetails login(@RequestBody CustomerModel customerModel) throws Exception {
        log.info("Logging in user:{}",customerModel);
        String rawPassword = customerModel.getPassword();
        UserDetails user = loginService.loginUser(customerModel);
        boolean passwordChecker = bCryptPasswordEncoder.matches(rawPassword,user.getPassword());
        if(!passwordChecker){
            throw new Exception("Invalid credentials");
        }
        return user;
    }
}
