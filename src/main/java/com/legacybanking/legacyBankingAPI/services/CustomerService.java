package com.legacybanking.legacyBankingAPI.services;
import com.legacybanking.legacyBankingAPI.Repos.CustomerRepo;
import com.legacybanking.legacyBankingAPI.models.Customer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {

       private final static String USER_NOT_FOUND = "invalid email or password";
       @Autowired
       private final CustomerRepo customerRepo;
       private final BCryptPasswordEncoder bCryptPasswordEncoder;

       @Override
       public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
           System.out.println(email);
           return customerRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
       }


       public boolean signUpCustomer(Customer customer){
           boolean usedEmail = customerRepo.findByEmail(customer.getEmail()).isPresent();
           if(usedEmail){
               throw new IllegalStateException("Email is taken");
           }
           String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
           customer.setPassword(encodedPassword);
           customerRepo.save(customer);
           return true;
       }
}
