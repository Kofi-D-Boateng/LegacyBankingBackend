package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.Repos.CustomerRole;
import com.legacybanking.legacyBankingAPI.models.Customer;
import com.legacybanking.legacyBankingAPI.models.CustomerModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerService {

       private final static String USER_NOT_FOUND = "invalid email or password";
       @Autowired
       private final CustomerRepo customerRepo;
       private final BCryptPasswordEncoder bCryptPasswordEncoder;

       public Customer getCustomerInfo(String username) throws UsernameNotFoundException{
           Optional<Customer> customer = customerRepo.findByEmail(username);

           return customer.orElseThrow(()->{
               throw new UsernameNotFoundException(USER_NOT_FOUND);
           });
       }

       public Customer loginUser(String username) throws UsernameNotFoundException{
           Optional<Customer> customer = customerRepo.findByEmail(username);
           return customer.orElseThrow(()->{
               throw new UsernameNotFoundException(USER_NOT_FOUND);
           });
       }

//       @Override
//       public Customer loadUserByUsername(String username) throws UsernameNotFoundException {
//
//       }


       public boolean signUpCustomer(@NotNull CustomerModel customerModel){
           boolean usedEmail = customerRepo.findByEmail(customerModel.getEmail()).isPresent();
           int random1 = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
           int random2 = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
           Long cardNumber = (long)(Math.random()*(99999999L * 10000000 +1)+ 10000);
           Integer cvc = new Random().nextInt(1000);
           String accountNumber = "1200" + random1;
           String routingNumber = "0533" + random2;

           if(usedEmail){
               throw new IllegalStateException("Email is taken");
           }
           customerModel.setCapital(0.0);
           String encryptedPassword = bCryptPasswordEncoder.encode(customerModel.getPassword());
           String encryptedSocialSecurity = bCryptPasswordEncoder.encode(customerModel.getSocialSecurity());
           String encryptedAccountNumber = bCryptPasswordEncoder.encode(accountNumber);
           String encryptedRoutingNumber = bCryptPasswordEncoder.encode(routingNumber);

           Customer customer = new Customer(
                   customerModel.getFirstName(),
                   customerModel.getLastName(),
                   encryptedPassword,
                   customerModel.getDob(),
                   customerModel.getEmail(),
                   customerModel.getCountry(),
                   customerModel.getState(),
                   customerModel.getZipcode(),
                   encryptedSocialSecurity,
                   customerModel.getCapital(),
                   customerModel.getPhoneNumber(),
                   encryptedAccountNumber,
                   encryptedRoutingNumber,
                   cardNumber,
                   cvc,
                   true,
                   false,
                   CustomerRole.USER
           );
           customerRepo.save(customer);
           return true;
       }
}
