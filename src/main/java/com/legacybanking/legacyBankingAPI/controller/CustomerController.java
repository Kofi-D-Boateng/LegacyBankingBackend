package com.legacybanking.legacyBankingAPI.controller;

import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.services.CustomerService;
import com.legacybanking.legacyBankingAPI.services.SecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
@RequestMapping(path = "/api/v2/customer/")
@AllArgsConstructor
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SecurityService securityService;

    @GetMapping("profile")
    @ResponseBody
    public Customer getCustomer(@RequestParam String username )throws UsernameNotFoundException {
        log.info("Username: {}", username);
        return customerService.getCustomerInfo(username);
    }
}
