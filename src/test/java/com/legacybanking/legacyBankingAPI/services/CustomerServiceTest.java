package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.ConfirmationTokenRepo;
import com.legacybanking.legacyBankingAPI.Repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.Repos.CustomerRole;
import com.legacybanking.legacyBankingAPI.models.Customer;
import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import com.legacybanking.legacyBankingAPI.models.VerificationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    Customer customer;
    CustomerModel customerModel;
    VerificationToken verificationToken;

    @InjectMocks
    CustomerService customerService;


    @Mock
    CustomerRepo customerRepo;
    @Mock
    ConfirmationTokenRepo confirmationTokenRepo;
    @Mock
    ConfirmationTokenService confirmationTokenService;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;



    @BeforeEach
    public void setUp(){
        customer = new Customer("Jon","Doe","Password", LocalDate.now(),"email@email.com"
                                ,"The United States","Texas",76012L,"000-00-0000"
                                ,50000.00D,1000000000L,"120034198",
                    "053351821",123453920185762L,134,false,
                    true, CustomerRole.USER);

        customerModel = new CustomerModel("Jon","Doe","Password", LocalDate.now(),"email@email.com"
                ,"The United States","Texas",76012L,"000-00-0000",0.0D,1000000000L);

        verificationToken = new VerificationToken("232jej21ej2e2", LocalDateTime.now(),LocalDateTime.now().plusMinutes(16),customer);
    }

    @Test
    void getCustomerInfo() {
        when(customerRepo.findByEmail("email@email.com")).thenReturn(Optional.ofNullable(customer));
        Customer customer = customerService.getCustomerInfo("email@email.com");
        assertEquals("Jon",customer.getFirstName());

    }

    @Test
    void loginUser() {
        when(customerRepo.findByEmail("email@email.com")).thenReturn(Optional.ofNullable(customer));
        Customer customer = customerService.loginUser("email@email.com");
        assertEquals("Jon",customer.getFirstName());

    }

    @Test
    void signUpCustomer() {
        int random1 = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
        int random2 = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
        Long cardNumber = (long)(Math.random()*(99999999L * 10000000 +1)+ 10000);
        Integer cvc = new Random().nextInt(1000);
        String accountNumber = "1200" + random1;
        String routingNumber = "0533" + random2;
        String encryptedPassword = bCryptPasswordEncoder.encode(customerModel.getPassword());
        String encryptedSocialSecurity = bCryptPasswordEncoder.encode(customerModel.getSocialSecurity());
        String encryptedAccountNumber = bCryptPasswordEncoder.encode(accountNumber);
        String encryptedRoutingNumber = bCryptPasswordEncoder.encode(routingNumber);
        lenient().when(customerRepo.findByEmail("email@email.com")).thenReturn(Optional.empty());
        lenient().when(bCryptPasswordEncoder.encode(customerModel.getPassword())).thenReturn(encryptedPassword);
        lenient().when(bCryptPasswordEncoder.encode(customerModel.getSocialSecurity())).thenReturn(encryptedSocialSecurity);
        lenient().when(bCryptPasswordEncoder.encode(accountNumber)).thenReturn(encryptedAccountNumber);
        lenient().when(bCryptPasswordEncoder.encode(routingNumber)).thenReturn(routingNumber);

        String token = customerService.signUpCustomer(customerModel);

        assertTrue(token.trim().length() != 0);
    }

    @Test
    void confirmAccount() {
        when(customerRepo.findByAccountNumber("120034198")).thenReturn(customer);
        boolean isVerified = customerService.confirmAccount(verificationToken);
        assertTrue(isVerified);
    }

    @Test
    void generateToken() {
        when(customerRepo.findByEmail("email@email.com")).thenReturn(Optional.ofNullable(customer));
        String token = customerService.generateToken(customer.getEmail());
        assertTrue(token.trim().length() != 0);
    }
}