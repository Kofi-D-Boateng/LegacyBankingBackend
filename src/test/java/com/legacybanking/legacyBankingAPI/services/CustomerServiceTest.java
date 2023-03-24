package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.enums.*;
import com.legacybanking.legacyBankingAPI.models.EmailAttributes;
import com.legacybanking.legacyBankingAPI.models.accounts.CheckingAccount;
import com.legacybanking.legacyBankingAPI.models.accounts.CreditAccount;
import com.legacybanking.legacyBankingAPI.models.accounts.SavingsAccount;
import com.legacybanking.legacyBankingAPI.models.cards.CreditCard;
import com.legacybanking.legacyBankingAPI.models.cards.DebitCard;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.CheckingAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.CreditAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.accountRepos.SavingsAccountRepo;
import com.legacybanking.legacyBankingAPI.repos.cardRepos.CreditCardRepo;
import com.legacybanking.legacyBankingAPI.repos.cardRepos.DebitCardRepo;
import com.legacybanking.legacyBankingAPI.repos.tokenRepo.ConfirmationTokenRepo;
import com.legacybanking.legacyBankingAPI.repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.models.user.Registration;
import com.legacybanking.legacyBankingAPI.models.securityAndTokens.VerificationToken;
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
    Registration registration;
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
    DebitCardRepo debitCardRepo;
    @Mock
    CreditCardRepo creditCardRepo;
    @Mock
    CreditAccountRepo creditAccountRepo;
    @Mock
    CheckingAccountRepo checkingAccountRepo;
    @Mock
    SavingsAccountRepo savingsAccountRepo;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;



    @BeforeEach
    public void setUp(){
        customer = new Customer("Jon","Doe","Password",LocalDate.now(),"email@email.com"
                ,"The United States","Texas",77777L,"000-00-0000",1000000000L, UserRole.CUSTOMER, Department.NONE,false,2453L);

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
    void confirmAccount() {
        when(customerRepo.findByEmail(customer.getEmail())).thenReturn(Optional.ofNullable(customer));
        boolean isVerified = customerService.confirmAccount(verificationToken);
        assertTrue(isVerified);
    }

    @Test
    void generateToken() {
        when(customerRepo.findByEmail("email@email.com")).thenReturn(Optional.ofNullable(customer));
        String token = customerService.generateToken(customer.getEmail());
        assertTrue(token.trim().length() != 0);
    }

    @Test
    void registerCustomer() {
        registration = new Registration("Jon","Doe","Password", LocalDate.now(),"email@email.com"
                ,"The United States","Texas",76012L,"000-00-0000",0.0D,1000000000L, BankAccountType.CHECKING, CardType.DEBIT,null,76053L,null,null,null,30.0);
        int random1 = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
        int random2 = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
        Long cardNumber = (long)(Math.random()*(99999999L * 10000000 +1)+ 10000);
        Integer cvc = new Random().nextInt(1000);
        String accountNumber = "1200" + random1;
        String routingNumber = "0533" + random2;
        String encryptedPassword = bCryptPasswordEncoder.encode(registration.getPassword());
        String encryptedSocialSecurity = bCryptPasswordEncoder.encode(registration.getSocialSecurity());
        lenient().when(customerRepo.findByEmail("email@email.com")).thenReturn(Optional.empty());
        lenient().when(bCryptPasswordEncoder.encode(registration.getPassword())).thenReturn(encryptedPassword);
        lenient().when(bCryptPasswordEncoder.encode(registration.getSocialSecurity())).thenReturn(encryptedSocialSecurity);
        lenient().when(customerRepo.save(customer)).thenReturn(customer);
        EmailAttributes attributes = customerService.registerCustomer(registration);

        assertNotNull(attributes);
    }


    @Test
    void testConfirmAccount() {
    }


    @Test
    void createCheckingAccount() {
        registration = new Registration("Jon","Doe","Password", LocalDate.now(),"email@email.com"
                ,"The United States","Texas",76012L,"000-00-0000",0.0D,1000000000L, BankAccountType.CHECKING, CardType.DEBIT,null,76053L,null,null,null,30.0);
        lenient().when(customerRepo.findByEmail("email@email.com")).thenReturn(Optional.of(customer));
        lenient().when(customerRepo.save(customer)).thenReturn(customer);
        lenient().when(debitCardRepo.save(new DebitCard())).thenReturn(new DebitCard());
        lenient().when(checkingAccountRepo.save(new CheckingAccount())).thenReturn(new CheckingAccount());

        String result = customerService.createCheckingAccount(registration);
        assertEquals("Successful",result);
    }

    @Test
    void createCreditAccount() {
        registration = new Registration("Jon","Doe","Password", LocalDate.now(),"email@email.com"
                ,"The United States","Texas",76012L,"000-00-0000",0.0D,1000000000L, BankAccountType.CREDIT, CardType.CREDIT, CreditType.PLATINUM,76053L,28.9,null,null,null);
        lenient().when(customerRepo.findByEmail("email@email.com")).thenReturn(Optional.of(customer));
        lenient().when(customerRepo.save(customer)).thenReturn(customer);
        lenient().when(creditCardRepo.save(new CreditCard())).thenReturn(new CreditCard());
        lenient().when(creditAccountRepo.save(new CreditAccount())).thenReturn(new CreditAccount());

        String result = customerService.createCreditAccount(registration);
        assertEquals("Successful",result);
    }

    @Test
    void createSavingsAccount() {
        registration = new Registration("Jon","Doe","Password", LocalDate.now(),"email@email.com"
                ,"The United States","Texas",76012L,"000-00-0000",0.0D,1000000000L, BankAccountType.SAVINGS, null,null,76053L,null,3.5,5000.00,null);
        lenient().when(customerRepo.findByEmail("email@email.com")).thenReturn(Optional.of(customer));
        lenient().when(savingsAccountRepo.save(new SavingsAccount())).thenReturn(new SavingsAccount());
        when(customerRepo.save(customer)).thenReturn(customer);

        String result = customerService.createSavingsAccount(registration);
        assertEquals("Successful",result);
    }
}