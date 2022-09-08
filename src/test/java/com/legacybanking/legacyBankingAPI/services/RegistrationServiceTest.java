package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.models.Customer;
import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import com.legacybanking.legacyBankingAPI.models.SecurityModel;
import com.legacybanking.legacyBankingAPI.models.VerificationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    CustomerModel customerModel;
    VerificationToken verificationToken;
    SecurityModel securityModel;

    @Mock
    CustomerService customerService;
    @Mock
    ConfirmationTokenService confirmationTokenService;

    @InjectMocks
    RegistrationService registrationService;

    @BeforeEach
    void setUp(){
        customerModel = new CustomerModel("Jon","Doe","Password", LocalDate.now(),"email@email.com"
                ,"The United States","Texas",76012L,"000-00-0000",0.0D,1000000000L);
        securityModel = new SecurityModel("232jej21ej2e2",true,false,"120034198");
        verificationToken = new VerificationToken("232jej21ej2e2", LocalDateTime.now(),LocalDateTime.now().plusMinutes(16),new Customer());
    }

    @Test
    void register() {
        when(customerService.signUpCustomer(customerModel)).thenReturn("232jej21ej2e2");

        String token = registrationService.register(customerModel);

        assertEquals("232jej21ej2e2",token);

    }

    @Test
    void verifyAccount() {
        when(confirmationTokenService.retrieveToken("232jej21ej2e2")).thenReturn(verificationToken);
        when(customerService.confirmAccount(verificationToken)).thenReturn(verificationToken.getCreatedAt().isBefore(verificationToken.getExpiresAt()));

        boolean check = registrationService.verifyAccount(securityModel);
        assertTrue(check);
    }

    @Test
    void generateToken() {
        when(customerService.generateToken("email@email.com")).thenReturn("232jej21ej2e2");
        String token = registrationService.generateToken(customerModel.getEmail());

        assertEquals(verificationToken.getToken(),token);
    }
}