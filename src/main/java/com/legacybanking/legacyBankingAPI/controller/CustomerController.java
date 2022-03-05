package com.legacybanking.legacyBankingAPI.controller;

import com.legacybanking.legacyBankingAPI.Repos.CustomerRole;
import com.legacybanking.legacyBankingAPI.models.Customer;
import com.legacybanking.legacyBankingAPI.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/customer/")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("registration")
    public boolean register(@RequestBody Customer request){
        Customer newCustomer = new Customer(
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getDob(),
                request.getEmail(),
                request.getCountry(),
                request.getState(),
                request.getZipcode(),
                request.getSocialSecurity(),
                request.getCapital(),
                CustomerRole.USER
        );
        return customerService.signUpCustomer(newCustomer);
    }

    @PostMapping("login")
    public UserDetails login(@RequestBody Customer customer){
        String email = customer.getEmail();
        System.out.println("email = " + email);
        return customerService.loadUserByUsername(email);
    }

}
