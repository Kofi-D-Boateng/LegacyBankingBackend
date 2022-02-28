package com.legacybanking.legacyBankingAPI.registration;

import com.legacybanking.legacyBankingAPI.customer.Customer;
import com.legacybanking.legacyBankingAPI.customer.CustomerRole;
import com.legacybanking.legacyBankingAPI.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final CustomerService customerService;
    private final EmailValidator emailValidator;


    public String register(RegistrationRequest request) {

        boolean isValid = emailValidator.test(request.getEmail());
        if(!isValid){
            throw new IllegalStateException("Invalid email");
        }
        return customerService.signUpCustomer(new Customer(
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getDob(),
                request.getEmail(),
                request.getCountry(),
                request.getState(),
                request.getZipcode(),
                request.getSocialSecurity(),
                CustomerRole.USER
        ));
    }
}
