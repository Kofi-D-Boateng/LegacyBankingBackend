package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Interfaces.CustomerServices;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.CreditType;
import com.legacybanking.legacyBankingAPI.enums.Department;
import com.legacybanking.legacyBankingAPI.enums.UserRole;
import com.legacybanking.legacyBankingAPI.models.AccountFactory;
import com.legacybanking.legacyBankingAPI.models.CardFactory;
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
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@Slf4j
public class CustomerService implements CustomerServices {

    private final static String INVALID_EMAIL_OR_PASSWORD = "invalid email or password";
    private final static String USER_NOT_FOUND = "Customer does not exist";
    private final static String UNSUCCESSFUL = "Unsuccessful";
    private final static String SUCCESSFUL = "Successful";
    private final static Double EMERALD_CREDIT_LINE = 5000.00D;
    private final static Double PLATINUM_CREDIT_LINE = 10000.00D;
    private final static Double BLACK_CREDIT_LINE = 100000.00D;

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ConfirmationTokenRepo tokenRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CheckingAccountRepo checkingAccountRepo;
    @Autowired
    private SavingsAccountRepo savingsAccountRepo;
    @Autowired
    private CreditAccountRepo creditAccountRepo;
    @Autowired
    private DebitCardRepo debitCardRepo;
    @Autowired
    private CreditCardRepo creditCardRepo;

    @Override
    public EmailAttributes registerCustomer(@NotNull Registration model) {
        Optional<Customer> customer = customerRepo.findByEmail(model.getEmail());
        if(customer.isPresent()) return null;
        String token = UUID.randomUUID().toString();
        String encryptedPassword = bCryptPasswordEncoder.encode(model.getPassword());
        String encryptedSocialSecurity = bCryptPasswordEncoder.encode(model.getSocialSecurity());
        Customer newCustomer = new Customer(model.getFirstName(),
                model.getLastName(),encryptedPassword,model.getDob(), model.getEmail(),
                model.getCountry(), model.getState(), model.getZipcode(), encryptedSocialSecurity,
                model.getPhoneNumber(), UserRole.CUSTOMER, Department.NONE, false, model.getCustomerPin());
        VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(16),
                newCustomer
        );
        try {
            customerRepo.save(newCustomer);
            tokenRepo.save(verificationToken);
            return new EmailAttributes(token,newCustomer.getFirstName()+newCustomer.getLastName(),newCustomer.getEmail());
        }catch (IllegalStateException e){
            log.info("Error: {}",e);
            return null;
        }
    }

    public Customer getCustomerInfo(String username) throws UsernameNotFoundException{
           Optional<Customer> customer = customerRepo.findByEmail(username);
           return customer.orElseThrow(()->{
               throw new UsernameNotFoundException(INVALID_EMAIL_OR_PASSWORD);
           });
    }
    public Customer loginUser(String username) throws UsernameNotFoundException{
        Optional<Customer> customer = customerRepo.findByEmail(username);
        return customer.orElseThrow(()->{
            throw new UsernameNotFoundException(INVALID_EMAIL_OR_PASSWORD);
        });
    }


    public Boolean confirmAccount(@NotNull VerificationToken VT) {
           Optional<Customer> customer = customerRepo.findByEmail(VT.getCustomer().getEmail());
           if(customer.isEmpty()) return false;
           VT.setConfirmedAt(LocalDateTime.now());
           customer.get().setIsActivated(true);
           customerRepo.save(customer.get());
           tokenRepo.save(VT);
           return true;
    }

    public String generateToken(String email) {
           String token = UUID.randomUUID().toString();
           Optional<Customer> customer = customerRepo.findByEmail(email);
           if(customer.isEmpty()){
               return UNSUCCESSFUL;
           }
           VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(16),
                customer.get()
           );
           tokenRepo.save(verificationToken);

           return token;
    }

    @Override
    public String createCheckingAccount(Registration model) {
        Optional<Customer> customer = customerRepo.findByEmail(model.getEmail());
        model.setCapital(0.0);
        if(customer.isEmpty()){
            return USER_NOT_FOUND;
        }
        try{
            AccountFactory accountFactory = new AccountFactory();
            CardFactory cardFactory = new CardFactory();
            CheckingAccount checkingAccount = (CheckingAccount) accountFactory.createAccount(model.getBankAccountType(),customer.get(),
                    null,null,null,model.getMinimumBalanceAllowed(),null);
            DebitCard debitCard = (DebitCard) cardFactory.createCard(CardType.DEBIT,null,customer.get());


            checkingAccount.setCard(debitCard);
            checkingAccount.setIsEnabled(true);
            debitCard.setAccount(checkingAccount);
            customer.get().getAccounts().add(checkingAccount);
            customer.get().getCards().add(debitCard);
            debitCardRepo.save(debitCard);
            checkingAccountRepo.save(checkingAccount);
            customerRepo.save(customer.get());


            return SUCCESSFUL;
        }catch (Error e){
            log.info("Error: {}",e);
            return UNSUCCESSFUL;
        }

    }

    @Override
    public String createCreditAccount(@NotNull Registration model) {
        Optional<Customer> customer = customerRepo.findByEmail(model.getEmail());
        if(customer.isEmpty()){
            return USER_NOT_FOUND;
        }

        try{
            AccountFactory accountFactory = new AccountFactory();
            CardFactory cardFactory = new CardFactory();
            CreditAccount creditAccount = (CreditAccount) accountFactory.createAccount(model.getBankAccountType(),customer.get(),null,null,
                    model.getAnnualPercentageRate(),null,model.getCreditType());
            CreditCard creditCard = (CreditCard) cardFactory.createCard(CardType.CREDIT,model.getCreditType(),customer.get());

            if(creditCard.getCreditType().equals(CreditType.EMERALD)){
                creditAccount.setCard(creditCard);
                creditCard.setAccount(creditAccount);
                creditAccount.setCapital(EMERALD_CREDIT_LINE);
            }else if(creditCard.getCreditType().equals(CreditType.BLACK)){
                creditAccount.setCard(creditCard);
                creditCard.setAccount(creditAccount);
                creditAccount.setCapital(BLACK_CREDIT_LINE);
            }else if(creditCard.getCreditType().equals(CreditType.PLATINUM)){
                creditAccount.setCard(creditCard);
                creditCard.setAccount(creditAccount);
                creditAccount.setCapital(PLATINUM_CREDIT_LINE);
            }

            customer.get().getAccounts().add(creditAccount);
            customer.get().getCards().add(creditCard);
            creditAccount.setIsEnabled(true);

            creditCardRepo.save(creditCard);
            creditAccountRepo.save(creditAccount);
            customerRepo.save(customer.get());

            return SUCCESSFUL;
        }catch (Error e){
            log.info("Error: {}",e);
            return UNSUCCESSFUL;
        }

    }

    @Override
    public String createSavingsAccount(Registration model) {
        Optional<Customer> customer = customerRepo.findByEmail(model.getEmail());
        if(customer.isEmpty()){
            return USER_NOT_FOUND;
        }

        try{
            AccountFactory accountFactory = new AccountFactory();
            CardFactory cardFactory = new CardFactory();
           SavingsAccount savingsAccount = (SavingsAccount) accountFactory.createAccount(model.getBankAccountType(),customer.get(),
                   model.getInterestRate(),model.getMaxAllowedContribution(),null,null,null);


            savingsAccount.setCard(null);
            customer.get().getAccounts().add(savingsAccount);

            savingsAccountRepo.save(savingsAccount);
            customerRepo.save(customer.get());

            return SUCCESSFUL;
        }catch (Error e){
            log.info("Error: {}",e);
            return UNSUCCESSFUL;
        }
    }
}
