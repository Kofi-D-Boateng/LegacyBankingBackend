package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.models.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerService {

       private final static String USER_NOT_FOUND = "invalid email or password";
       @Autowired
       private final CustomerRepo customerRepo;
       private final BCryptPasswordEncoder bCryptPasswordEncoder;

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


       public boolean signUpCustomer(@NotNull Customer customer){
           boolean usedEmail = customerRepo.findByEmail(customer.getEmail()).isPresent();
           int random1 = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
           int random2 = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
           String accountNumber = "1200" + random1;
           String routingNumber = "0533" + random2;
           if(usedEmail){
               throw new IllegalStateException("Email is taken");
           }
           customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
           customer.setAccountNumber(bCryptPasswordEncoder.encode(accountNumber));
           customer.setRoutingNumber(bCryptPasswordEncoder.encode(routingNumber));
           customer.setSocialSecurity(bCryptPasswordEncoder.encode(customer.getSocialSecurity()));
           customer.setEnabled(false);
           customer.setLocked(true);
           customerRepo.save(customer);
           return true;
       }
}
